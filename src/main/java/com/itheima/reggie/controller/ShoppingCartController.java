package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description:
 * @author: ChenZhao
 * @Date 2023/11/15 17:53
 * @since 1.0.0
 * TODO:
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车：{}", shoppingCart);

        //设置用户id，指定当前时哪个用户的购物车数据
        Long userId= BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //判断当前传来的Id是菜品还是套餐，这两个肯定会有一个是Null
        Long dishId=shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);

        //动态拼接一下添加的查询条件
        if (dishId!=null){
            //传过来的是菜品而不是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId);
        }
        if (setmealId!=null){
            //传过来的是套餐而不是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
        }
        /*
          SQL:select * from shopping_cart where user_Id=? and dish_Id=?/setmealId=?
          如果可以查出来，说明购物车已经加入了相关菜品
         */
        ShoppingCart cartServiceOne=shoppingCartService.getOne(lambdaQueryWrapper);

        //已经存在在购物车里
        if (cartServiceOne!=null){
            //在数量原有基础上+1
            Integer count = cartServiceOne.getNumber();
            cartServiceOne.setNumber(count + 1);
            /*
                update shopping_cart set number=（更新后数量）
            */
            shoppingCartService.updateById(cartServiceOne);
        }else {
            //尚未存在购物车，就添加到购物车
            shoppingCart.setNumber(1);
            /*
                insert into shopping_cart (ShoppingCart解析出来的字段) values (ShoppingCart解析出来的数据)
            */
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            //因为这个分支的cartServiceOne是null，所以要覆盖一下
            cartServiceOne = shoppingCart;
        }
        return R.success(cartServiceOne);

    }
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        log.info("查看购物车");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseContext.getCurrentId()!=null,ShoppingCart::getUserId, BaseContext.getCurrentId());

        // 最晚下单的 菜品或套餐在购物车中最先展示
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 一次性清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //获取当前购物车用户Id
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
        shoppingCartService.remove(lambdaQueryWrapper);
        return R.success("清空成功");
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){

        //设置用户id，指定当前时哪个用户的购物车数据
        Long userId= BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //判断当前传来的Id是菜品还是套餐，这两个肯定会有一个是Null
        Long dishId=shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);

        //动态拼接一下添加的查询条件
        if (dishId!=null){
            //传过来的是菜品而不是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId);
        }
        if (setmealId!=null){
            //传过来的是套餐而不是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
        }
        /*
          SQL:select * from shopping_cart where user_Id=? and dish_Id=?/setmealId=?
          如果可以查出来，说明购物车已经加入了相关菜品
         */
        ShoppingCart cartServiceOne=shoppingCartService.getOne(lambdaQueryWrapper);

        if(cartServiceOne != null){

            //数量大于1
            if (cartServiceOne.getNumber() > 1){
                //在数量原有基础上-1
                Integer count = cartServiceOne.getNumber();
                cartServiceOne.setNumber(count - 1);
            /*
                update shopping_cart set number=（更新后数量）
            */
                shoppingCartService.updateById(cartServiceOne);
                return R.success(cartServiceOne);
            }else {
                //只剩1个，直接删除

                shoppingCartService.remove(lambdaQueryWrapper);
                return R.success(cartServiceOne);
            }
        }
        return null;
    }


}
