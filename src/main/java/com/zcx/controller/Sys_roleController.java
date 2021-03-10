package com.zcx.controller;

import com.zcx.pojo.Sys_role;
import com.zcx.pojo.Sys_role_permission;
import com.zcx.service.Sys_roleService;
import lombok.Getter;
import lombok.Setter;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("/sys_role")
public class Sys_roleController {
    @Autowired
    private Sys_roleService sys_roleService;

    @Getter
    @Setter
    static class RoleAndPermission {
        @Valid
        @NotNull
        private Sys_role sys_role;
        @Valid
        @NotNull
        private List<Sys_role_permission> sys_role_permissionList;
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated RoleAndPermission roleAndPermission,
                                         BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ControllerUtils.errorMsg(allErrors));
        Integer affectedRows =
                sys_roleService.insert(roleAndPermission.getSys_role(), roleAndPermission.getSys_role_permissionList());
        return ResponseEntity.ok(affectedRows);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Sys_role>> findAll() {
        List<Sys_role> sys_roleList = sys_roleService.findAll();
        return ResponseEntity.ok(sys_roleList);
    }

    @PostMapping("/update")
    public ResponseEntity<Integer> update(@RequestBody Sys_role sys_role) {
        Integer affectedRows = sys_roleService.update(sys_role);
        return ResponseEntity.ok(affectedRows);
    }
}
