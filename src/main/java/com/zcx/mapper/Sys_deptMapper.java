package com.zcx.mapper;

import com.zcx.pojo.Sys_dept;

import java.util.List;

public interface Sys_deptMapper {
    List<Sys_dept> findAll();
    Integer insert(Sys_dept dept);
    Integer update(Sys_dept sys_dept);
    Sys_dept findById(Integer deptid);
}
