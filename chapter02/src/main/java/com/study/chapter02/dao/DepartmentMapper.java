package com.study.chapter02.dao;

import com.study.chapter02.bean.Department;

public interface DepartmentMapper {

    Department getDeptById(Integer id);

    Department getDeptByIdPlus(Integer id);

    Department getDeptByIdStep(Integer deptId);
}
