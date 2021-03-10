package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Sys_role_permission {
    private Integer sys_role_id;
    @NotNull
    private Integer sys_permission_id;
}
