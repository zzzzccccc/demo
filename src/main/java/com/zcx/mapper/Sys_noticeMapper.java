package com.zcx.mapper;

import com.zcx.pojo.Sys_notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Sys_noticeMapper {
    List<Sys_notice> findPage(Page<Sys_notice> page);
    Integer totalRows();
    Integer insert(Sys_notice sys_notice);
    Integer update(Sys_notice sys_notice);
    Integer deletes(@Param("ids") Map<Integer, Object> ids);
}
