package com.zcx.service;

import com.zcx.mapper.Page;
import com.zcx.mapper.Sys_userMapper;
import com.zcx.pojo.Sys_user;
import com.zcx.pojo.Sys_user_role;
import com.zcx.utils.ErrorMsg;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Sys_userService {
    @Autowired
    private Sys_userMapper sys_userMapper;

    @Value("${pwd}")
    private String pwd;

    @Value("${imgpath}")
    private String imgpath;

    public boolean validateLoginnameAndPwd(String loginname, String pwd) {
        if (StringUtils.isEmpty(loginname) || StringUtils.isEmpty(pwd)) return false;

        loginname = loginname.trim();
        pwd = pwd.trim();
        Sys_user user = sys_userMapper.findUserByLoginname(loginname);
        if (user == null) return false;
        if (!user.getPwd().equals(DigestUtils.md5DigestAsHex(pwd.getBytes()))) return false;

        return true;
    }

    public Sys_user findUserById(Integer id) {
        if (id == null) return null;

        return sys_userMapper.findUserById(id);
    }

    public Sys_user findUserByLoginname(String loginname) {
        if (StringUtils.isEmpty(loginname)) return null;
        loginname = loginname.trim();
        Sys_user user = sys_userMapper.findUserByLoginname(loginname);
        return user;
    }

    public List<Sys_user> findUserByDeptId(Integer deptId) {
        if (deptId == null) return null;
        return sys_userMapper.findUserByDeptId(deptId);
    }

    public Map findPage(Sys_user sys_user, Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;

        Integer offset = (currentPage - 1) * rows;
        List<Sys_user> sys_userList = sys_userMapper.findPage(new Page<>(sys_user, offset, rows));
        if (sys_userList == null || sys_userList.size() < 1) return null;

        return ServiceUtils.pageResult(sys_userList, sys_userMapper.totalRows());
    }

    public Integer insert(Sys_user sys_user, List<Sys_user_role> sys_user_roleList) {
        if (sys_user == null) return 0;
        Sys_user sysUser = findUserByLoginname(sys_user.getLoginname());
        if (sysUser != null) return 0;
        if (sys_user_roleList == null || sys_user_roleList.size() < 1) return 0;
        sys_user.setOrdernum(System.currentTimeMillis());
        sys_user.setPwd(pwd);
        sys_user.setImgpath(imgpath);
        Integer affectedRows = sys_userMapper.insert(sys_user);
        if (affectedRows < 1) return affectedRows;
        for (Sys_user_role sys_user_role : sys_user_roleList) {
            sys_user_role.setSys_user_id(sys_user.getId());
        }
        Integer affectedRows1 = sys_userMapper.insertUserRoles(sys_user_roleList);
        if (affectedRows1 < 1) ServiceUtils.rollback("[sys_userMapper.insertUserRoles] inserted result that affectedRows is 0");
        return affectedRows;
    }

    public Object update(Sys_user sys_user) {
        if (sys_user == null || sys_user.getId() == null) return 0;
        if (!StringUtils.isEmpty(sys_user.getPwd())) {
            boolean isTrue = sys_user.getPwd().matches("^[0-9]+$");
            if (!isTrue) return new ErrorMsg("密码必须纯数字");
            sys_user.setPwd(DigestUtils.md5DigestAsHex(sys_user.getPwd().getBytes()));
        }
        return sys_userMapper.update(sys_user);
    }

    public Object updatePwd(Sys_user sys_user) {
        if (sys_user == null || StringUtils.isEmpty(sys_user.getLoginname())) return 0;
        if (StringUtils.isEmpty(sys_user.getPwd())) return 0;
        boolean isTrue = sys_user.getPwd().matches("^[0-9]+$");
        if (!isTrue) return new ErrorMsg("密码必须纯数字");
        sys_user.setPwd(DigestUtils.md5DigestAsHex(sys_user.getPwd().getBytes()));
        return sys_userMapper.updatePwd(sys_user);
    }
}
