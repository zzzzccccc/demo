package com.zcx.controller;

import com.zcx.pojo.Bus_export;
import com.zcx.pojo.Bus_import;
import com.zcx.service.Bus_exportService;
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
@RequestMapping("/bus_export")
public class Bus_exportController {
    @Autowired
    private Bus_exportService bus_exportService;

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated Bus_export bus_export, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            return ResponseEntity.badRequest().body(ControllerUtils.errorMsg(allErrors));
        }
        Object result = bus_exportService.insert(bus_export);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Bus_export bus_export) {
        Object result = bus_exportService.update(bus_export);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        Object result = bus_exportService.deleteById(id);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Bus_export> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(bus_exportService.findById(id));
    }

    @PostMapping("/findPage")
    public ResponseEntity<Map> findPage(@RequestBody ControllerUtils.Page<Bus_export> page) {
        return ResponseEntity.ok(bus_exportService.findPage(page.getConditions(),
                page.getCurrentPage(), page.getRows()));
    }
}
