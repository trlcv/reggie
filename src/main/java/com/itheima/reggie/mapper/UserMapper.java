package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: ChenZhao
 * @Date 2023/11/14 16:57
 * @since 1.0.0
 * TODO:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
