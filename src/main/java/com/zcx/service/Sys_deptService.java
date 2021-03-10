package com.zcx.service;

import com.zcx.mapper.Sys_deptMapper;
import com.zcx.pojo.Sys_dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class Sys_deptService {
    @Autowired
    private Sys_deptMapper sys_deptMapper;

    public List<Sys_dept> findAll() {
        return sys_deptMapper.findAll();
    }

    public Integer insert(Sys_dept dept) {
        if (dept == null) return 0;
        if ("".equals(dept.getRemark())) dept.setRemark(null);
        dept.setCreatetime(System.currentTimeMillis());
        dept.setOrdernum(System.currentTimeMillis());
        return sys_deptMapper.insert(dept);
    }

    public Integer update(Sys_dept sys_dept) {
        if (sys_dept == null || sys_dept.getId() == null) return 0;
        return sys_deptMapper.update(sys_dept);
    }
}
