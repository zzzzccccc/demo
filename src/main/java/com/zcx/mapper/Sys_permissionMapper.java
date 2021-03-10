package com.zcx.mapper;

import com.zcx.pojo.Sys_permission;

import java.util.List;

public interface Sys_permissionMapper {
    List<Sys_permission> findPermissionByUserId(Integer id);
    List<Sys_permission> findAll();
    List<Sys_permission> findPermissionByRoleId(Integer sys_role_id);
}
