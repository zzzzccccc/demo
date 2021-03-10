package com.zcx.service;

import com.zcx.mapper.Sys_roleMapper;
import com.zcx.pojo.Sys_role;
import com.zcx.pojo.Sys_role_permission;
import com.zcx.utils.ServiceUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionOperations;

import java.util.List;

@Service
public class Sys_roleService {
    @Autowired
    private Sys_roleMapper sys_roleMapper;

    public Integer insert(Sys_role sys_role, List<Sys_role_permission> sys_role_permissionList) {
        if (sys_role == null || sys_role_permissionList == null || sys_role_permissionList.size() < 1) return 0;
        if ("".equals(sys_role.getRemark())) sys_role.setRemark(null);
        Integer affectedRows = sys_roleMapper.insert(sys_role);
        if (affectedRows < 1) return 0;
        for (Sys_role_permission sys_role_permission : sys_role_permissionList) sys_role_permission.setSys_role_id(sys_role.getId());
        Integer result = sys_roleMapper.insertRolePermission(sys_role_permissionList);
        if (result < 1) ServiceUtils.rollback("[sys_roleMapper.insertRolePermission] inserted result that affectedRows is 0");
        return affectedRows;
    }

    public List<Sys_role> findAll() {
        return sys_roleMapper.findAll();
    }

    public Integer update(Sys_role sys_role) {
        if (sys_role == null || sys_role.getId() == null) return 0;
        return sys_roleMapper.update(sys_role);
    }
}
