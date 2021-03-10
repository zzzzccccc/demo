package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Sys_dept {
    private Integer id;
    @NotNull
    private Integer pid;
    @NotNull
    private String title;
    @NotNull
    private Integer open; //1打开 0关闭
    private String remark;
    private Integer available;
    private Long ordernum;
    private Long createtime;
}
