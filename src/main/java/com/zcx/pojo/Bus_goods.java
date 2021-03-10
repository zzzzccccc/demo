package com.zcx.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Bus_goods {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Integer providerid;
    private Bus_provider bus_provider;
    private String produce_place;
    private String size;
    private String packaging;
    private String code;
    private String description;
    @Min(0)
    private Double price;
    @NotNull
    @Min(0)
    private Integer number;
    @NotNull
    @Min(0)
    private Integer dangernum;
    private String img;
    private Integer available;
}
