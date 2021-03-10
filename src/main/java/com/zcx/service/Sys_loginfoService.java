package com.zcx.service;

import com.zcx.mapper.Sys_loginfoMapper;
import com.zcx.pojo.Sys_loginfo;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Sys_loginfoService {
    @Autowired
    private Sys_loginfoMapper sys_loginfoMapper;

    public Map findPage(Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;

        Map<String, Integer> params = new HashMap<>();
        params.put("offset", (currentPage - 1) * rows);
        params.put("rows", rows);
        List<Sys_loginfo> sys_loginfoMapperList = sys_loginfoMapper.findPage(params);
        if (sys_loginfoMapperList == null || sys_loginfoMapperList.size() < 1) return null;

        return ServiceUtils.pageResult(sys_loginfoMapperList, sys_loginfoMapper.totalRows());
    }

    public Integer deletes(List<Integer> ids) {
        if (ids == null || ids.size() < 1) return 0;

        Map<Integer, Object> params = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            params.put(ids.get(i), null);
        }
        return sys_loginfoMapper.deletes(params);
    }

    public Integer insert(Sys_loginfo sys_loginfo) {
        if (sys_loginfo == null) return 0;
        return sys_loginfoMapper.insert(sys_loginfo);
    }
}

