package com.zcx.mapper;

import com.zcx.pojo.Bus_export;

import java.util.List;

public interface Bus_exportMapper {
    Integer insert(Bus_export bus_export);
    Integer update(Bus_export bus_export);
    Bus_export findById(Integer id);
    Integer deleteById(Integer id);
    List<Bus_export> findPage(Page<Bus_export> page);
    Integer totalRows();
}
