package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Sys_user_role {
    private Integer sys_user_id;
    @NotNull
    private Integer sys_role_id;
}
