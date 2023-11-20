package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * @description:
 * @author: ChenZhao
 * @Date 2023/11/10 14:49
 * @since 1.0.0
 * TODO:
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
