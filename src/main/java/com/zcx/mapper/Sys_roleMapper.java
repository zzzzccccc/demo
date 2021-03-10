package com.zcx.mapper;

import com.zcx.pojo.Sys_role;
import com.zcx.pojo.Sys_role_permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Sys_roleMapper {
    List<Sys_role> findAll();
    List<Sys_role> findRoleByUserId(Integer id);
    Integer insert(Sys_role sys_role);
    Integer insertRolePermission(List<Sys_role_permission> sys_role_permissionList);
    Integer update(Sys_role sys_role);
}
