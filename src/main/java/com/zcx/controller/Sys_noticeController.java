package com.zcx.controller;

import com.auth0.jwt.interfaces.Claim;
import com.zcx.pojo.Sys_notice;
import com.zcx.service.Sys_noticeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys_notice")
public class Sys_noticeController {
    @Autowired
    private Sys_noticeService sys_noticeService;

    @Getter
    @Setter
    private static class Sys_noticePage {
        private Sys_notice sys_notice;
        private Integer currentPage;
        private Integer rows;
    }

    @PostMapping("/findPage")
    public ResponseEntity<Map<String, Object>> findPage(@RequestBody ControllerUtils.Page<Sys_notice> sys_noticePage) {
        Map<String, Object> result = sys_noticeService.findPage(sys_noticePage.getConditions(),
                sys_noticePage.getCurrentPage(), sys_noticePage.getRows());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated Sys_notice sys_notice, BindingResult bindingResult,
                                         HttpServletRequest request) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ControllerUtils.errorMsg(allErrors));
        }

        Map<String, Claim> claims = (Map<String, Claim>) request.getAttribute("claims");
        Integer influencedRow = sys_noticeService.insert(sys_notice, claims.get("loginname").asString());
        return ResponseEntity.status(HttpStatus.OK).body(influencedRow);
    }

    @PostMapping("/update")
    public ResponseEntity<Integer> update(@RequestBody Sys_notice notice) {
        Integer influencedRows = sys_noticeService.update(notice);
        return ResponseEntity.status(HttpStatus.OK).body(influencedRows);
    }

    @PostMapping("/deletes")
    public ResponseEntity<Integer> deletes(@RequestBody List<Integer> ids) {
        Integer affectedRows = sys_noticeService.deletes(ids);
        return ResponseEntity.status(HttpStatus.OK).body(affectedRows);
    }
}
