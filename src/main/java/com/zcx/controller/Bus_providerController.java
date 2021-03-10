package com.zcx.controller;

import com.zcx.pojo.Bus_provider;
import com.zcx.service.Bus_providerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus_provider")
public class Bus_providerController {
    @Autowired
    private Bus_providerService bus_providerService;

    @PostMapping("/insert")
    public ResponseEntity<Integer> insert(@RequestBody Bus_provider bus_provider) {
        return ResponseEntity.ok(bus_providerService.insert(bus_provider));
    }

    @PostMapping("/deletes")
    public ResponseEntity<Integer> deletes(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(bus_providerService.deletes(ids));
    }

    @PostMapping("/findPage")
    public ResponseEntity<Map> findPage(@RequestBody ControllerUtils.Page<Bus_provider> page) {
        return ResponseEntity.
                ok(bus_providerService.findPage(page.getConditions(), page.getCurrentPage(), page.getRows()));
    }

    @PostMapping("/update")
    public ResponseEntity<Integer> update(@RequestBody Bus_provider bus_provider) {
        return ResponseEntity.ok(bus_providerService.update(bus_provider));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Bus_provider>> findAll() {
        return ResponseEntity.ok(bus_providerService.findAll());
    }
}
