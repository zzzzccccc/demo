package com.zcx.mapper;

import com.zcx.pojo.Sys_user;
import com.zcx.pojo.Sys_user_role;

import java.util.List;

public interface Sys_userMapper {
    Sys_user findUserById(Integer id);
    Sys_user findUserByLoginname(String loginname);
    List<Sys_user> findUserByDeptId(Integer deptId);
    List<Sys_user> findPage(Page<Sys_user> page);
    Integer totalRows();
    Integer insert(Sys_user sys_user);
    Integer insertUserRoles(List<Sys_user_role> sys_user_roleList);
    Integer update(Sys_user sys_user);
    Integer updatePwd(Sys_user sys_user);
}
