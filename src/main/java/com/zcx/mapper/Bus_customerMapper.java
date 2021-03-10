package com.zcx.mapper;

import com.zcx.pojo.Bus_customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Bus_customerMapper {
    Integer insert(Bus_customer bus_customer);
    Integer deletes(@Param("ids") Map<Integer, Object> ids);
    List<Bus_customer> findPage(Page<Bus_customer> page);
    Integer totalRows();
    Integer update(Bus_customer bus_customer);
}
