package com.zcx.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.zcx.mapper.Page;
import com.zcx.pojo.Sys_loginfo;
import com.zcx.pojo.Sys_permission;
import com.zcx.pojo.Sys_user;
import com.zcx.pojo.Sys_user_role;
import com.zcx.service.Sys_loginfoService;
import com.zcx.service.Sys_permissionService;
import com.zcx.service.Sys_userService;
import com.zcx.utils.ErrorMsg;
import com.zcx.utils.TokenUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys_user")
public class Sys_userController {
    @Autowired
    private Sys_userService sys_userService;

    @Autowired
    private Sys_permissionService sys_permissionService;

    @Autowired
    private Sys_loginfoService sys_loginfoService;

    @Value("${secret}")
    private String secret;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map result, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        boolean b = sys_userService.validateLoginnameAndPwd((String) result.get("loginname"), (String) result.get("pwd"));
        Map<String, String> rtnvalue = new HashMap<>();
        if (b) {
            Sys_user user = sys_userService.findUserByLoginname((String) result.get("loginname"));
            Map<String, Object> headerClaims = TokenUtils.getHeaderClaims();
            String token = JWT.create().withHeader(headerClaims).withClaim("loginname", user.getLoginname()).withClaim("id", user.getId())
                    .withClaim("name", user.getName()).withClaim("createTime", (double) System.currentTimeMillis()).
                    sign(Algorithm.HMAC256(secret));
            rtnvalue.put("msg", "登录成功");
            addLoginInfo(user.getLoginname(), request.getRemoteAddr());
            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setPath("/");
            response.addCookie(tokenCookie);
            return ResponseEntity.status(HttpStatus.OK).body(rtnvalue);
        } else {
            rtnvalue.put("msg", "账号或者密码不正确");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rtnvalue);
        }
    }

    private void addLoginInfo(String loginname, String ip) {
        Sys_loginfo sys_loginfo = new Sys_loginfo();
        sys_loginfo.setLoginname(loginname);
        sys_loginfo.setLoginip(ip);
        sys_loginfo.setLogintime(System.currentTimeMillis());
        sys_loginfoService.insert(sys_loginfo);
    }

    @GetMapping("/findPermissionByUserId/{id}")
    public ResponseEntity<List<Sys_permission>> findPermissionByUserId(@PathVariable Integer id) {
        List<Sys_permission> sys_permissions = sys_permissionService.findPermissionByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(sys_permissions);
    }

    @GetMapping("/findUserByDeptId")
    public ResponseEntity<List<Sys_user>> findUserByDeptId(@RequestBody Sys_user sys_user) {
        List<Sys_user> sys_userList = sys_userService.findUserByDeptId(sys_user.getDeptid());
        return ResponseEntity.status(HttpStatus.OK).body(sys_userList);
    }

    @PostMapping("/findPage")
    public ResponseEntity<Map> findPage(@RequestBody ControllerUtils.Page<Sys_user> params) {
        Map result =
                sys_userService.findPage(params.getConditions(), params.getCurrentPage(), params.getRows());
        return ResponseEntity.ok(result);
    }

    @Getter
    @Setter
    static class UserAndRoles {
        @NotNull
        @Valid
        private Sys_user sys_user;
        @NotNull
        @Valid
        private List<Sys_user_role> sys_user_roleList;
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insert(@RequestBody @Validated UserAndRoles userAndRoles, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            return ResponseEntity.badRequest().body(ControllerUtils.errorMsg(allErrors));
        }
        Integer affectedRows = sys_userService.insert(userAndRoles.getSys_user(), userAndRoles.getSys_user_roleList());
        return ResponseEntity.ok().body(affectedRows);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Sys_user sys_user) {
        Object result = sys_userService.update(sys_user);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/updatePwd")
    public ResponseEntity<Object> updatePwd(@RequestBody Sys_user sys_user) {
        Object result = sys_userService.updatePwd(sys_user);
        if (result instanceof ErrorMsg) return ResponseEntity.badRequest().body(((ErrorMsg) result).getMsg());
        return ResponseEntity.ok(result);
    }
}
