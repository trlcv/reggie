package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: ChenZhao
 * @Date 2023/11/15 21:33
 * @since 1.0.0
 * TODO:
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
