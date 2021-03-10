package com.zcx.service;

import com.zcx.mapper.Bus_customerMapper;
import com.zcx.mapper.Page;
import com.zcx.pojo.Bus_customer;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Bus_customerService {
    @Autowired
    private Bus_customerMapper bus_customerMapper;

    public Integer insert(Bus_customer bus_customer) {
        if (bus_customer == null) return 0;
        return bus_customerMapper.insert(bus_customer);
    }

    public Integer deletes(List<Integer> ids) {
        if (ids == null || ids.size() < 1) return 0;
        Map<Integer, Object> params = new HashMap<>();
        for (Integer id : ids) {
            params.put(id, null);
        }
        return bus_customerMapper.deletes(params);
    }

    public Map findPage(Bus_customer bus_customer, Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;
        List<Bus_customer> bus_customerList =
                bus_customerMapper.findPage(new Page<>(bus_customer, (currentPage - 1) * rows, rows));
        if (bus_customerList == null || bus_customerList.size() < 1) return null;
        return ServiceUtils.pageResult(bus_customerList, bus_customerMapper.totalRows());
    }

    public Integer update(Bus_customer bus_customer) {
        if (bus_customer == null || bus_customer.getId() == null) return 0;
        return bus_customerMapper.update(bus_customer);
    }


}
