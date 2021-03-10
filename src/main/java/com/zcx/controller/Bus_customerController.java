package com.zcx.controller;

import com.zcx.pojo.Bus_customer;
import com.zcx.service.Bus_customerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus_customer")
public class Bus_customerController {
    @Autowired
    private Bus_customerService bus_customerService;

    @PostMapping("/insert")
    public ResponseEntity<Integer> insert(@RequestBody Bus_customer bus_customer) {
        return ResponseEntity.ok(bus_customerService.insert(bus_customer));
    }

    @PostMapping("/deletes")
    public ResponseEntity<Integer> deletes(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(bus_customerService.deletes(ids));
    }

    @PostMapping("/findPage")
    public ResponseEntity<Map> findPage(@RequestBody ControllerUtils.Page<Bus_customer> page) {
        return ResponseEntity.
                ok(bus_customerService.findPage(page.getConditions(), page.getCurrentPage(), page.getRows()));
    }

    @PostMapping("/update")
    public ResponseEntity<Integer> update(@RequestBody Bus_customer bus_customer) {
        return ResponseEntity.ok(bus_customerService.update(bus_customer));
    }
}
