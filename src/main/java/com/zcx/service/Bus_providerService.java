package com.zcx.service;

import com.zcx.mapper.Bus_providerMapper;
import com.zcx.mapper.Page;
import com.zcx.pojo.Bus_provider;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Bus_providerService {
    @Autowired
    private Bus_providerMapper bus_providerMapper;

    public Integer insert(Bus_provider bus_provider) {
        if (bus_provider == null) return 0;
        return bus_providerMapper.insert(bus_provider);
    }

    public Integer deletes(List<Integer> ids) {
        if (ids == null || ids.size() < 1) return 0;
        Map<Integer, Object> params = new HashMap<>();
        for (Integer id : ids) {
            params.put(id, null);
        }
        return bus_providerMapper.deletes(params);
    }

    public Map findPage(Bus_provider bus_provider, Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;
        List<Bus_provider> bus_providerList =
                bus_providerMapper.findPage(new Page<>(bus_provider, (currentPage - 1) * rows, rows));
        if (bus_providerList == null || bus_providerList.size() < 1) return null;
        return ServiceUtils.pageResult(bus_providerList, bus_providerMapper.totalRows());
    }

    public Integer update(Bus_provider bus_provider) {
        if (bus_provider == null || bus_provider.getId() == null) return 0;
        return bus_providerMapper.update(bus_provider);
    }

    public List<Bus_provider> findAll() {
        return bus_providerMapper.findAll();
    }
}
