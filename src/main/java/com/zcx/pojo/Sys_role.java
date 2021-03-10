package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class Sys_role {
    private Integer id;
    @NotNull
    private String name;
    private String remark;
    private List<Sys_permission> sysPermissionList;
}
