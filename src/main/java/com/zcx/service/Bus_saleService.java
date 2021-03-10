package com.zcx.service;

import com.zcx.mapper.Bus_goodsMapper;
import com.zcx.mapper.Bus_saleMapper;
import com.zcx.mapper.Page;
import com.zcx.pojo.Bus_goods;
import com.zcx.pojo.Bus_sale;
import com.zcx.utils.ErrorMsg;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Bus_saleService {
    @Autowired
    private Bus_saleMapper bus_saleMapper;

    @Autowired
    private Bus_goodsMapper bus_goodsMapper;

    public Object insert(Bus_sale bus_sale) {
        if (bus_sale == null) return 0;
        Bus_goods bus_goods = new Bus_goods();
        bus_goods.setId(bus_sale.getGoods_id());
        bus_goods.setNumber(-bus_sale.getNumber());
        Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
        if (affectedRows < 1) return new ErrorMsg("更新商品数量失败");
        Double sale_price = bus_sale.getSale_price();
        long v = (long) (sale_price * 100);
        bus_sale.setSale_price((double) v / 100);
        Integer infectedRows = bus_saleMapper.insert(bus_sale);
        if (infectedRows < 1) return new ErrorMsg("添加失败");
        return infectedRows;
    }

    public Map findPage(Bus_sale bus_sale, Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;
        List<Bus_sale> bus_saleList =
                bus_saleMapper.findPage(new Page<>(bus_sale, (currentPage - 1) * rows, rows));
        if (bus_saleList == null || bus_saleList.size() < 1) return null;
        return ServiceUtils.pageResult(bus_saleList, bus_saleMapper.totalRows());
    }

    public Object update(Bus_sale bus_sale) {
        if (bus_sale == null || bus_sale.getId() == null) return 0;
        if (bus_sale.getNumber() != null) {
            Integer number = bus_sale.getNumber();
            if (number < 1) return new ErrorMsg("数量不可小于1");
            Bus_sale dbBus_sale = bus_saleMapper.findById(bus_sale.getId());
            if (dbBus_sale == null) return new ErrorMsg("没有从数据库中获取到bus_sale");
            Bus_goods bus_goods = new Bus_goods();
            bus_goods.setId(dbBus_sale.getGoods_id());
            bus_goods.setNumber(dbBus_sale.getNumber() - number);
            Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
            if (affectedRows < 1) return new ErrorMsg("更新商品数量失败");
        }
        if (bus_sale.getSale_price() != null) {
            Double sale_price = bus_sale.getSale_price();
            if (sale_price < 1) return new ErrorMsg("价格不可小于1");
            long v = (long) (sale_price * 100);
            bus_sale.setSale_price((double) v / 100);
        }
        Integer infectedRows = bus_saleMapper.update(bus_sale);
        if (infectedRows < 1) return new ErrorMsg("更新失败");
        return infectedRows;
    }

    public Object deleteById(Integer id) {
        if (id == null) return 0;
        Bus_sale dbBus_sale = bus_saleMapper.findById(id);
        if (dbBus_sale == null) return new ErrorMsg("没有从数据库中获取到bus_sale");
        Bus_goods bus_goods = new Bus_goods();
        bus_goods.setId(dbBus_sale.getGoods_id());
        bus_goods.setNumber(dbBus_sale.getNumber());
        Integer affectedRows = bus_goodsMapper.updateNumber(bus_goods);
        if (affectedRows < 1) return new ErrorMsg("更新商品数量失败");
        affectedRows = bus_saleMapper.deleteById(id);
        if (affectedRows < 1) ServiceUtils.rollback("删除失败");
        return affectedRows;
    }
}
