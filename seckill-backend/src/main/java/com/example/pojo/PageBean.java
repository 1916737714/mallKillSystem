package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageBean<T> {
    private Long total;
    private List<T> items;
}
