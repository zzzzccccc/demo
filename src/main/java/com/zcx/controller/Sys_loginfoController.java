package com.zcx.controller;

import com.zcx.pojo.Sys_loginfo;
import com.zcx.pojo.Sys_notice;
import com.zcx.service.Sys_loginfoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys_loginfo")
public class Sys_loginfoController {
    @Autowired
    private Sys_loginfoService sys_loginfoService;

    @PostMapping("/findPage")
    public ResponseEntity<Map> findPage(@RequestBody ControllerUtils.Page<Sys_loginfo> params) {
        return ResponseEntity.status(HttpStatus.OK).body(
                sys_loginfoService.findPage(params.getCurrentPage(), params.getRows())
        );
    }

    @PostMapping("/deletes")
    public ResponseEntity<Integer> deletes(@RequestBody List<Integer> ids) {
        Integer rowsAffected = sys_loginfoService.deletes(ids); // affected 受影响的
        return ResponseEntity.status(HttpStatus.OK).body(rowsAffected);
    }
}
