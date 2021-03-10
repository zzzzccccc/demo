package com.zcx.mapper;

import com.zcx.pojo.Sys_loginfo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Sys_loginfoMapper {
    List<Sys_loginfo> findPage(Map<String, Integer> params);
    Integer totalRows();
    Integer deletes(@Param("ids") Map<Integer, Object> ids); // @param 用来给参数命名
    Integer insert(Sys_loginfo sys_loginfo);
}
