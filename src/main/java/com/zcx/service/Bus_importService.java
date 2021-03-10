package com.zcx.service;

import com.zcx.mapper.Bus_goodsMapper;
import com.zcx.mapper.Bus_importMapper;
import com.zcx.mapper.Page;
import com.zcx.pojo.Bus_goods;
import com.zcx.pojo.Bus_import;
import com.zcx.utils.ErrorMsg;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Bus_importService {
    @Autowired
    private Bus_importMapper bus_importMapper;

    @Autowired
    private Bus_goodsMapper bus_goodsMapper;

    public Map findPage(Bus_import bus_import, Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;
        List<Bus_import> bus_importList =
                bus_importMapper.findPage(new Page<>(bus_import, (currentPage - 1) * rows, rows));
        if (bus_importList == null || bus_importList.size() < 1) return null;
        return ServiceUtils.pageResult(bus_importList, bus_importMapper.totalRows());
    }

    public Object insert(Bus_import bus_import) {
        if (bus_import == null) return 0;
        Bus_goods bus_goods = new Bus_goods();
        bus_goods.setId(bus_import.getGoods_id());
        bus_goods.setNumber(bus_import.getNumber());
        Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
        if (affectedRows < 1) return new ErrorMsg("更新仓库商品数量失败");
        Double import_price = bus_import.getImport_price();
        int v = (int) (import_price * 100);
        bus_import.setImport_price((double) v / 100);
        affectedRows = bus_importMapper.insert(bus_import);
        if (affectedRows < 1) ServiceUtils.rollback("插入bus_import记录失败");
        return affectedRows;
    }

    public Object update(Bus_import bus_import) {
        if (bus_import == null || bus_import.getId() == null) return 0;
        if (bus_import.getNumber() != null) {
            Integer number = bus_import.getNumber();
            if (number < 1) return new ErrorMsg("数量不能小于1");
            Bus_import dbBus_import = bus_importMapper.findById(bus_import.getId());
            if (dbBus_import == null) return new ErrorMsg("在数据库中找不到dbBus_import");
            Bus_goods bus_goods = new Bus_goods();
            bus_goods.setId(dbBus_import.getGoods_id());
            bus_goods.setNumber(bus_import.getNumber() - dbBus_import.getNumber());
            Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
            if (affectedRows < 1) return new ErrorMsg("更新仓库商品数量失败");
        }
        if (bus_import.getImport_price() != null) {
            Double import_price = bus_import.getImport_price();
            if (import_price < 1) return new ErrorMsg("购入商品价格不能小于1");
            int v = (int) (import_price * 100);
            bus_import.setImport_price((double) v / 100);
        }
        Integer affectedRows = bus_importMapper.update(bus_import);
        if (affectedRows < 1) ServiceUtils.rollback("更新bus_import记录失败");
        return affectedRows;
    }

    public Object deleteById(Integer id) {
        if (id == null) return 0;
        Bus_import dbBus_import = bus_importMapper.findById(id);
        if (dbBus_import == null) return new ErrorMsg("在数据库中找不到dbBus_import");
        Bus_goods bus_goods = new Bus_goods();
        bus_goods.setId(dbBus_import.getGoods_id());
        bus_goods.setNumber(-dbBus_import.getNumber());
        Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
        if (affectedRows < 1) return new ErrorMsg("更新仓库商品数量失败");
        affectedRows = bus_importMapper.deleteById(id);
        if (affectedRows < 1) ServiceUtils.rollback("删除bus_import记录失败");
        return affectedRows;
    }
}
