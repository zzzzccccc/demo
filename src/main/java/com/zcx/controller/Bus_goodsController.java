package com.zcx.controller;

import com.zcx.pojo.Bus_goods;
import com.zcx.service.Bus_goodsService;
import com.zcx.utils.ErrorMsg;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus_goods")
public class Bus_goodsController {
    @Autowired
    private Bus_goodsService bus_goodsService;

    @Value("${imagesAbsolutePath}")
    private String imagesAbsolutePath;

    @Value("${goods_uploadImgPath}")
    private String uploadImgPath;

    @Value("${imagesHost}")
    private String imagesHost;

    @Value("${goods_imgAccessPath}")
    private String imgAccessPath;

    @PostMapping("/findPage")
    public ResponseEntity<Map> findPage(@RequestBody ControllerUtils.Page<Bus_goods> page) {
        return ResponseEntity.
                ok(bus_goodsService.findPage(page.getConditions(), page.getCurrentPage(), page.getRows()));
    }

    @PostMapping("/uploadImg")
    public ResponseEntity<Map> uploadImg(@RequestParam("image") MultipartFile mf) throws IOException {
        String extensionName = FilenameUtils.getExtension(mf.getOriginalFilename());
        // 直接上传的图片为临时文件（加上_temp依旧可以访问），点击确定添加后修改文件名称，解决无效文件问题
        String filename = System.currentTimeMillis() + "." + extensionName + "_temp";
        mf.transferTo(new File(imagesAbsolutePath + uploadImgPath + filename));
        Map<String, Object> result = new HashMap<>();
        // 添加 images 解决拦截器拦截合适url问题
        result.put("imgAccessPath", imagesHost + imgAccessPath + filename);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated Bus_goods bus_goods, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ControllerUtils.errorMsg(allErrors));
        }
        Object result = bus_goodsService.insert(bus_goods);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Bus_goods bus_goods) {
        Object result = bus_goodsService.update(bus_goods);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Bus_goods> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(bus_goodsService.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Bus_goods>> findAll() {
        return ResponseEntity.ok(bus_goodsService.findAll());
    }

    @GetMapping("/findByProviderId/{provideId}")
    public ResponseEntity<List<Bus_goods>> findByProviderId(@PathVariable Integer provideId) {
        return ResponseEntity.ok(bus_goodsService.findByProviderId(provideId));
    }

    @PostMapping("/lessGoodsNotice")
    public ResponseEntity<List<Bus_goods>> lessGoodsNotice() {
        return ResponseEntity.ok(bus_goodsService.lessGoods());
    }
}
