package com.zcx.controller;

import com.zcx.pojo.Bus_sale;
import com.zcx.service.Bus_saleService;
import com.zcx.utils.ErrorMsg;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus_sale")
public class Bus_saleController {
    @Autowired
    private Bus_saleService bus_saleService;

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated Bus_sale bus_sale, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            return ResponseEntity.badRequest().body(ControllerUtils.errorMsg(allErrors));
        }
        Object res = bus_saleService.insert(bus_sale);
        if (res instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) res).getMsg());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/findPage")
    public ResponseEntity<Map> findPage(@RequestBody ControllerUtils.Page<Bus_sale> page) {
        return ResponseEntity.
                ok(bus_saleService.findPage(page.getConditions(), page.getCurrentPage(), page.getRows()));
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Bus_sale bus_sale) {
        Object res = bus_saleService.update(bus_sale);
        if (res instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) res).getMsg());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        Object res = bus_saleService.deleteById(id);
        if (res instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) res).getMsg());
        return ResponseEntity.ok(res);
    }
}
