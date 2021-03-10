package com.zcx.service;

import com.zcx.mapper.Bus_exportMapper;
import com.zcx.mapper.Bus_goodsMapper;
import com.zcx.mapper.Page;
import com.zcx.pojo.Bus_export;
import com.zcx.pojo.Bus_goods;
import com.zcx.pojo.Bus_import;
import com.zcx.utils.ErrorMsg;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Bus_exportService {
    @Autowired
    private Bus_exportMapper bus_exportMapper;

    @Autowired
    private Bus_goodsMapper bus_goodsMapper;

    public Object insert(Bus_export bus_export) {
        if (bus_export == null) return 0;
        Bus_goods bus_goods = new Bus_goods();
        bus_goods.setId(bus_export.getGoodsid());
        bus_goods.setNumber(-bus_export.getNumber());
        Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
        if (affectedRows < 1) return new ErrorMsg("更新仓库商品数量失败");
        int v = (int) (bus_export.getExport_price() * 100);
        bus_export.setExport_price((double) v / 100);
        affectedRows = bus_exportMapper.insert(bus_export);
        if (affectedRows < 1) ServiceUtils.rollback("插入bus_export记录失败");
        return affectedRows;
    }

    public Object update(Bus_export bus_export) {
        if (bus_export == null || bus_export.getId() == null) return 0;
        if (bus_export.getExport_price() != null) {
            Double export_price = bus_export.getExport_price();
            if (export_price < 1) return new ErrorMsg("export_price必须>=1");
            int v = (int) (export_price * 100);
            bus_export.setExport_price((double) v / 100);
        }
        if (bus_export.getNumber() != null) {
            Integer number = bus_export.getNumber();
            if (number < 1) return new ErrorMsg("数量不能小于1");
            Bus_export dbBus_export = bus_exportMapper.findById(bus_export.getId());
            if (dbBus_export == null) return new ErrorMsg("在数据库中找不到dbBus_export");
            Bus_goods bus_goods = new Bus_goods();
            bus_goods.setId(dbBus_export.getGoodsid());
            bus_goods.setNumber(dbBus_export.getNumber() - bus_export.getNumber());
            Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
            if (affectedRows < 1) return new ErrorMsg("更新仓库商品数量失败");
        }
        Integer affectedRows = bus_exportMapper.update(bus_export);
        if (affectedRows < 1) ServiceUtils.rollback("更新bus_export失败");
        return affectedRows;
    }

    public Object deleteById(Integer id) {
        if (id == null) return 0;
        Bus_export dbBus_export = bus_exportMapper.findById(id);
        if (dbBus_export == null) return new ErrorMsg("在数据库中找不到dbBus_export");
        Bus_goods bus_goods = new Bus_goods();
        bus_goods.setId(dbBus_export.getGoodsid());
        bus_goods.setNumber(dbBus_export.getNumber());
        Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
        if (affectedRows < 1) return new ErrorMsg("更新仓库商品数量失败");
        affectedRows = bus_exportMapper.deleteById(id);
        if (affectedRows < 1) ServiceUtils.rollback("删除bus_import记录失败");
        return affectedRows;
    }

    public Bus_export findById(Integer id) {
        if (id == null) return null;
        return bus_exportMapper.findById(id);
    }

    public Map findPage(Bus_export bus_export, Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;
        List<Bus_export> bus_exportList =
                bus_exportMapper.findPage(new Page<>(bus_export, (currentPage - 1) * rows, rows));
        if (bus_exportList == null || bus_exportList.size() < 1) return null;
        return ServiceUtils.pageResult(bus_exportList, bus_exportMapper.totalRows());
    }
}
