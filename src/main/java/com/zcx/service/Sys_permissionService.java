package com.zcx.service;

import com.zcx.mapper.Sys_permissionMapper;
import com.zcx.pojo.Sys_permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Sys_permissionService {
    @Autowired
    private Sys_permissionMapper sys_permissionMapper;

    public List<Sys_permission> findPermissionByUserId(Integer id) {
        if (id == null) return null;
        return sys_permissionMapper.findPermissionByUserId(id);
    }

    public List<Sys_permission> findAll() {
        return sys_permissionMapper.findAll();
    }
}
