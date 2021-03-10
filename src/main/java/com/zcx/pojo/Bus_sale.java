package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Bus_sale {
    private Integer id;
    @NotNull
    private Integer customer_id;
    @NotNull
    private String paytype;
    @NotNull
    private Long sale_time;
    @NotNull
    private String operatorperson;
    @NotNull
    @Min(1)
    private Integer number;
    private String remark;
    @NotNull
    @Min(1)
    private Double sale_price;
    @NotNull
    private Integer goods_id;
}
