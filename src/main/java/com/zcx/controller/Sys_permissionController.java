package com.zcx.controller;

import com.zcx.pojo.Sys_permission;
import com.zcx.service.Sys_permissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sys_permission")
public class Sys_permissionController {
    @Autowired
    private Sys_permissionService sys_permissionService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Sys_permission>> findAll() {
        List<Sys_permission> sys_permissionList = sys_permissionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sys_permissionList);
    }
}
