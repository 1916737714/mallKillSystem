package com.example.pojo;

import lombok.Data;

@Data
public class BaseQueryDTO {
    private Integer currentPage;
    private Integer pageSize;
    private String key;
}
