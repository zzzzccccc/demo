package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Sys_notice {
    private Integer id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private Long createtime;
    private String opername;
}
