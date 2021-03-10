package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sys_permission {
    private Integer id;
    private Integer pid;
    private String type;
    private String title;
    private String percode;
    private String icon;
    private String href;
    private String target;
    private Integer open;
    private Integer ordernum;
}
