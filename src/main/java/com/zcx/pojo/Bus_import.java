package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Bus_import {
    private Integer id;
    @NotNull
    private String paytype;
    @NotNull
    private Long import_time;
    @NotNull
    private String operater;
    @NotNull
    private Integer goods_id;
    private Bus_goods bus_goods;
    @NotNull
    @Min(1)
    private Double import_price;
    @NotNull
    @Min(1)
    private Integer number;
    @NotNull
    private Integer provider_id;
    private Bus_provider bus_provider;
    private String remark;

    private Long startImportTime; // 用于条件查询import_time(买入时间)
    private Long endImportTime;
}
