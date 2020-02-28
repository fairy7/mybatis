package com.study.chapter02.dao;

import com.study.chapter02.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    /*多条记录封装一个map:Map<Integer, Employee>,key是主键，value是封装后的JavaBean*/
    //告诉mybatis封装这个map的时候使用那个属性作为map的key
    @MapKey("id")
    Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);

    /*返回一条记录的map;key就是列明，值就是对应的值*/
    Map<String, Object> getEmpByIdReturnMap(Integer id);

    List<Employee> getEmpsByLastNameLike(String lastName);

    Employee getEmpByMap(Map<String, Object> map);

    Employee getEmpByIdAndName(@Param("id") Integer id, @Param("lastName") String name);

    Employee getEmpById(Integer id);

    void addEmp(Employee employee);

    Boolean updateEmp(Employee employee);

    void deleteEmp(Employee employee);

}
