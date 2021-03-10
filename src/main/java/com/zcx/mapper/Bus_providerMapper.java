package com.zcx.mapper;

import com.zcx.pojo.Bus_provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Bus_providerMapper {
    Integer insert(Bus_provider bus_provider);
    Integer deletes(@Param("ids") Map<Integer, Object> ids);
    List<Bus_provider> findPage(Page<Bus_provider> page);
    Integer totalRows();
    Integer update(Bus_provider bus_provider);
    Bus_provider findById(Integer id);
    List<Bus_provider> findAll();
}
