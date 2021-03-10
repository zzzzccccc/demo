package com.zcx.mapper;

import com.zcx.pojo.Bus_import;

import java.util.List;

public interface Bus_importMapper {
    List<Bus_import> findPage(Page<Bus_import> page);
    Integer totalRows();
    Integer insert(Bus_import bus_import);
    Integer update(Bus_import bus_import);
    Bus_import findById(Integer id);
    Integer deleteById(Integer id);
}
