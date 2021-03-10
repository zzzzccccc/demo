package com.zcx.service;

import com.zcx.mapper.Page;
import com.zcx.mapper.Sys_noticeMapper;
import com.zcx.pojo.Sys_notice;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Sys_noticeService {
    @Autowired
    private Sys_noticeMapper sys_noticeMapper;

    public Map findPage(Sys_notice sys_notice, Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;

        Integer offset = (currentPage - 1) * rows;
        List<Sys_notice> sys_noticeList = sys_noticeMapper.findPage(new Page<>(sys_notice, offset, rows));
        if (sys_noticeList == null || sys_noticeList.size() < 1) return null;

        return ServiceUtils.pageResult(sys_noticeList, sys_noticeMapper.totalRows());
    }

    public Integer insert(Sys_notice sys_notice, String opername) {
        if (sys_notice == null) return 0;
        sys_notice.setOpername(opername);
        sys_notice.setCreatetime(System.currentTimeMillis());
        return sys_noticeMapper.insert(sys_notice);
    }

    public Integer update(Sys_notice notice) {
        if (notice == null || notice.getId() == null) return 0;
        return sys_noticeMapper.update(notice);
    }

    public Integer deletes(List<Integer> ids) {
        if (ids == null || ids.size() < 1) return 0;
        Map<Integer, Object> params = new HashMap<>();
        for (Integer id : ids) {
            params.put(id, null);
        }
        return sys_noticeMapper.deletes(params);
    }
}
