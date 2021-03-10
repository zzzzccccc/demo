package com.zcx.mapper;

import com.zcx.pojo.Bus_saleback;

import java.util.List;

public interface Bus_salebackMapper {
    Integer insert(Bus_saleback bus_saleback);
    List<Bus_saleback> findPage(Page<Bus_saleback> page);
    Integer totalRows();
    Integer update(Bus_saleback bus_saleback);
    Bus_saleback findById(Integer id);
    Integer deleteById(Integer id);
}
