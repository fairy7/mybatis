package com.study.chapter02.dao;

import com.study.chapter02.bean.Employee;
import org.apache.ibatis.annotations.Select;

public interface EmployeeMapperAnnotation {

    @Select(value = "select * from employee where id = #{id}")
    Employee getEmpById(Integer id);

}
