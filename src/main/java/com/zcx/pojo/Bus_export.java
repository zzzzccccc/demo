package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Bus_export {
    private Integer id;
    @NotNull
    private Integer providerid;
    private Bus_provider bus_provider;
    @NotNull
    private String paytype;
    @NotNull
    private Long export_time;
    @NotNull
    @Min(1)
    private Double export_price;
    @NotNull
    @Min(1)
    private Integer number;
    private String  remark;
    @NotNull
    private Integer goodsid;
    private Bus_goods bus_goods;

    private Long startExportTime;
    private Long endExportTIme;
}
