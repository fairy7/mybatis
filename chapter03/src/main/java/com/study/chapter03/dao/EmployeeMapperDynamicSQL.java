package com.study.chapter03.dao;

import com.study.bean.Employee;

import java.util.List;

public interface EmployeeMapperDynamicSQL {

    List<Employee> getEmpsByConditionIf(Employee employee);

}
