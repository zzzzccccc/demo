package com.zcx.mapper;

import com.zcx.pojo.Bus_goods;

import java.util.List;

public interface Bus_goodsMapper {
    List<Bus_goods> findPage(Page<Bus_goods> page);
    Integer totalRows();
    Integer insert(Bus_goods bus_goods);
    Integer update(Bus_goods bus_goods);
    Bus_goods findById(Integer id);
    List<Bus_goods> findAll();
    Integer updateNumber(Bus_goods bus_goods);
    List<Bus_goods> findByProviderId(Integer providerid);
    List<Bus_goods> lessGoods();
}
