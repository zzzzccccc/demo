package com.zcx.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private T obj;
    private Integer offset;
    private Integer rows;
}
