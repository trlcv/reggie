package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Orders;

/**
 * @description:
 * @author: ChenZhao
 * @Date 2023/11/15 21:35
 * @since 1.0.0
 * TODO:
 */

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
