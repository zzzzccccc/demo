package com.zcx.controller;

import com.zcx.pojo.Bus_saleback;
import com.zcx.service.Bus_salebackService;
import com.zcx.utils.ErrorMsg;
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
@RequestMapping("/bus_saleback")
public class Bus_salebackController {
    @Autowired
    private Bus_salebackService bus_salebackService;

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated Bus_saleback bus_saleback, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            return ResponseEntity.badRequest().body(ControllerUtils.errorMsg(allErrors));
        }
        Object res = bus_salebackService.insert(bus_saleback);
        if (res instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) res).getMsg());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/findPage")
    public ResponseEntity<Map> findPage(@RequestBody ControllerUtils.Page<Bus_saleback> page) {
        return ResponseEntity.
                ok(bus_salebackService.findPage(page.getConditions(), page.getCurrentPage(), page.getRows()));
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Bus_saleback bus_saleback) {
        Object res = bus_salebackService.update(bus_saleback);
        if (res instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) res).getMsg());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        Object res = bus_salebackService.deleteById(id);
        if (res instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) res).getMsg());
        return ResponseEntity.ok(res);
    }
}
