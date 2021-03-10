package com.zcx.controller;

import com.zcx.pojo.Sys_dept;
import com.zcx.service.Sys_deptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/sys_dept")
public class Sys_deptController {
    @Autowired
    private Sys_deptService sys_deptService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Sys_dept>> findAll() {
        List<Sys_dept> sys_deptList = sys_deptService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sys_deptList);
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated Sys_dept sys_dept, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ControllerUtils.errorMsg(allErrors));
        }

        Integer affectedRows = sys_deptService.insert(sys_dept);
        return ResponseEntity.status(HttpStatus.OK).body(affectedRows);
    }

    @PostMapping("/update")
    public ResponseEntity<Integer> update(@RequestBody Sys_dept dept) {
        Integer affectedRows = sys_deptService.update(dept);
        return ResponseEntity.status(HttpStatus.OK).body(affectedRows);
    }
}
