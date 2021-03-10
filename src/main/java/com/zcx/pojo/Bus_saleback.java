package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Bus_saleback {
    private Integer id;
    @NotNull
    private Integer customer_id;
    @NotNull
    private String paytype;
    @NotNull
    private Long backtime;
    @NotNull
    @Min(1)
    private Double backprice;
    private String operaterperson;
    @NotNull
    @Min(1)
    private Integer number;
    private String remark;
    @NotNull
    private Integer goods_id;
    private Integer version;
}
