package com.study.chapter01.dao;

import com.study.chapter01.bean.Employee;
import org.apache.ibatis.annotations.Select;

public interface EmployeeMapperAnnotation {

    @Select(value = "select * from employee where id = #{id}")
    Employee getEmpById(Integer id);

}
