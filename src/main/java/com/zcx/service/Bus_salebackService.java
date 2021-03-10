package com.zcx.service;

import com.zcx.mapper.Bus_goodsMapper;
import com.zcx.mapper.Bus_salebackMapper;
import com.zcx.mapper.Page;
import com.zcx.pojo.Bus_goods;
import com.zcx.pojo.Bus_sale;
import com.zcx.pojo.Bus_saleback;
import com.zcx.utils.ErrorMsg;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Bus_salebackService {
    @Autowired
    private Bus_salebackMapper bus_salebackMapper;

    @Autowired
    private Bus_goodsMapper bus_goodsMapper;

    public Object insert(Bus_saleback bus_saleback) {
        if (bus_saleback == null) return 0;
        Bus_goods bus_goods = new Bus_goods();
        bus_goods.setId(bus_saleback.getGoods_id());
        bus_goods.setNumber(bus_saleback.getNumber());
        Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
        if (affectedRows < 1) {
            return new ErrorMsg("更新商品数量失败");
        }
        bus_saleback.setVersion(1);
        Double backprice = bus_saleback.getBackprice();
        long v = (long) (backprice * 100);
        bus_saleback.setBackprice((double) v / 100);
        affectedRows = bus_salebackMapper.insert(bus_saleback);
        if (affectedRows < 1) ServiceUtils.rollback("添加销售返回记录失败");
        return affectedRows;
    }

    public Map findPage(Bus_saleback bus_saleback, Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;
        List<Bus_saleback> bus_salebackList =
                bus_salebackMapper.findPage(new Page<>(bus_saleback, (currentPage - 1) * rows, rows));
        if (bus_salebackList == null || bus_salebackList.size() < 1) return null;
        return ServiceUtils.pageResult(bus_salebackList, bus_salebackMapper.totalRows());
    }

    public Object update(Bus_saleback bus_saleback) {
        if (bus_saleback == null || bus_saleback.getId() == null) return 0;
        if (bus_saleback.getBackprice() != null) {
            Double backprice = bus_saleback.getBackprice();
            if (backprice < 1) return new ErrorMsg("价格不能小于1");
            long v = (long) (backprice * 100);
            bus_saleback.setBackprice((double) v / 100);
        }
        if (bus_saleback.getNumber() != null) {
            Integer number = bus_saleback.getNumber();
            if (number < 1) return new ErrorMsg("数量不能小于1");
            Bus_saleback dbBus_saleback = bus_salebackMapper.findById(bus_saleback.getId());
            if (dbBus_saleback == null) return new ErrorMsg("没有从数据库中获取到dbBus_saleback");
            Bus_goods bus_goods = new Bus_goods();
            bus_goods.setId(dbBus_saleback.getGoods_id());
            bus_goods.setNumber(number - dbBus_saleback.getNumber());
            Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
            if (affectedRows < 1) return new ErrorMsg("更新商品数量失败");
            bus_saleback.setVersion(dbBus_saleback.getVersion());
        }
        Integer affectedRows1 = bus_salebackMapper.update(bus_saleback);
        if (affectedRows1 < 1) ServiceUtils.rollback("更新bus_saleback记录失败");
        return affectedRows1;
    }

    public Object deleteById(Integer id) {
        if (id == null) return 0;
        Bus_saleback dbBus_saleback = bus_salebackMapper.findById(id);
        if (dbBus_saleback == null) return new ErrorMsg("没有从数据库中获取到dbBus_saleback");
        Bus_goods bus_goods = new Bus_goods();
        bus_goods.setId(dbBus_saleback.getGoods_id());
        bus_goods.setNumber(-dbBus_saleback.getNumber());
        Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
        if (affectedRows < 1) return new ErrorMsg("更新商品数量失败");
        affectedRows = bus_salebackMapper.deleteById(id);
        if (affectedRows < 1) ServiceUtils.rollback("删除bus_saleback记录失败");
        return affectedRows;
    }
}
