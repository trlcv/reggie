package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: ChenZhao
 * @Date 2023/11/8 10:53
 * @since 1.0.0
 * TODO:
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
