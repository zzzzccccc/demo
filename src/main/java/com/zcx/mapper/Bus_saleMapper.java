package com.zcx.mapper;

import com.zcx.pojo.Bus_sale;

import java.util.List;

public interface Bus_saleMapper {
    Integer insert(Bus_sale bus_sale);
    List<Bus_sale> findPage(Page<Bus_sale> page);
    Integer totalRows();
    Integer update(Bus_sale bus_sale);
    Bus_sale findById(Integer id);
    Integer deleteById(Integer id);
}
