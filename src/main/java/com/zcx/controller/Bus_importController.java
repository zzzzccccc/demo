package com.zcx.controller;

import com.zcx.pojo.Bus_import;
import com.zcx.service.Bus_importService;
import com.zcx.utils.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus_import")
public class Bus_importController {
    @Autowired
    private Bus_importService bus_importService;

    @PostMapping("/findPage")
    public ResponseEntity<Map> findPage(@RequestBody ControllerUtils.Page<Bus_import> page) {
        return ResponseEntity.ok(bus_importService.findPage(page.getConditions(),
                page.getCurrentPage(), page.getRows()));
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated Bus_import bus_import, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ControllerUtils.errorMsg(allErrors));
        }

        Object result = bus_importService.insert(bus_import);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Bus_import bus_import) {
        Object result = bus_importService.update(bus_import);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        Object result = bus_importService.deleteById(id);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok(result);
    }
}
