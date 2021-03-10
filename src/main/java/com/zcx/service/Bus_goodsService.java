package com.zcx.service;

import com.zcx.mapper.Bus_goodsMapper;
import com.zcx.mapper.Page;
import com.zcx.pojo.Bus_goods;
import com.zcx.utils.ErrorMsg;
import com.zcx.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class Bus_goodsService {
    @Autowired
    private Bus_goodsMapper bus_goodsMapper;

    @Value("${imagesAbsolutePath}")
    private String imagesAbsolutePath;

    @Value("${goods_defaultImg}")
    private String defaultImg;

    public Map findPage(Bus_goods bus_goods, Integer currentPage, Integer rows) {
        if (currentPage == null || currentPage < 1) return null;
        if (rows == null || rows < 1) return null;
        List<Bus_goods> bus_goodsList =
                bus_goodsMapper.findPage(new Page<>(bus_goods, (currentPage - 1) * rows, rows));
        if (bus_goodsList == null || bus_goodsList.size() < 1) return null;
        return ServiceUtils.pageResult(bus_goodsList, bus_goodsMapper.totalRows());
    }

    public Object insert(Bus_goods bus_goods) {
        if (bus_goods == null) return 0;
        if (bus_goods.getNumber() <= bus_goods.getDangernum()) return new ErrorMsg("商品数量必须大于危险数");
        if (!StringUtils.isEmpty(bus_goods.getImg())) {
            String img = bus_goods.getImg();
            if (img.endsWith("_temp")) {
                int indexOf = img.indexOf("images/");
                String filepath = imagesAbsolutePath + img.substring(indexOf + 7);
                String newfilepath = filepath.substring(0, filepath.indexOf("_temp"));
                boolean succeed = new File(filepath).renameTo(new File(newfilepath));
                if (!succeed) return new ErrorMsg("重命名文件失败");
                bus_goods.setImg(img.substring(0, img.indexOf("_temp")));
            }
        }
        if (bus_goods.getPrice() != null) {
            Double price = bus_goods.getPrice();
            int v = (int) (price * 100);
            bus_goods.setPrice((double) v / 100);
        }
        Integer infectedRows = bus_goodsMapper.insert(bus_goods);
        if (infectedRows < 1) ServiceUtils.rollback("添加失败");
        return infectedRows;
    }

    public Object update(Bus_goods bus_goods) {
        if (bus_goods == null || bus_goods.getId() == null) return 0;
        if (bus_goods.getPrice() != null) {
            Double price = bus_goods.getPrice();
            if (price < 0) return new ErrorMsg("price必须大于等于0");
            int v = (int) (price * 100);
            bus_goods.setPrice((double) v / 100);
        }
        if (!StringUtils.isEmpty(bus_goods.getImg())) {
            String img = bus_goods.getImg();
            if (img.endsWith("_temp")) { // 重新上传了一张图片
                Bus_goods dbBus_goods = bus_goodsMapper.findById(bus_goods.getId());
                String dbImg = dbBus_goods.getImg();
                int index = -1;
                if (!dbImg.endsWith(defaultImg)) {
                    index = dbImg.indexOf("images/");
                    File oldFile = new File(imagesAbsolutePath + dbImg.substring(index + 7));
                    oldFile.delete();
                }
                index = img.indexOf("images/");
                String filepath = imagesAbsolutePath + img.substring(index + 7);
                String newfilepath = filepath.substring(0, filepath.indexOf("_temp"));
                File diskFile = new File(filepath);
                boolean succeed = diskFile.renameTo(new File(newfilepath));
                if (!succeed) return new ErrorMsg("重命名文件失败");
                bus_goods.setImg(img.substring(0, img.lastIndexOf("_temp")));
            }
        }
        Integer affectedRows = bus_goodsMapper.update(bus_goods);
        if (affectedRows < 1) ServiceUtils.rollback("更新失败");
        return affectedRows;
    }

    public Bus_goods findById(Integer id) {
        if (id == null) return null;
        return bus_goodsMapper.findById(id);
    }

    public List<Bus_goods> findAll() {
        return bus_goodsMapper.findAll();
    }

    public List<Bus_goods> findByProviderId(Integer providerId) {
        if (providerId == null) return null;
        return bus_goodsMapper.findByProviderId(providerId);
    }

    public List<Bus_goods> lessGoods() {
        return bus_goodsMapper.lessGoods();
    }
}
