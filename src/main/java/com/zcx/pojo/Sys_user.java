package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
public class Sys_user {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @Pattern(regexp = "^[a-z]+$", message = "只允许为a-z") // ^表示要以a-z开始 $表示要以a-z结束 +表示a-z可以出现一次或多次 [a-z]表示a-z中任意一个字符
    private String loginname;
    private String address;
    @NotNull
    private String sex;
    private String remark;
    private String pwd;
    @NotNull
    private Integer deptid;
    private Sys_dept sys_dept;
    @NotNull
    private Long hiredate; // hire 租用
    @NotNull
    private Integer mgr;
    private Sys_user mgrSys_user;
    private Integer available;
    private Long ordernum;
    private String imgpath; // 头像地址
    private List<Sys_role> sys_roleList;
}
