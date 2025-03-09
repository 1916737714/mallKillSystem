package com.example.pojo;

import lombok.Data;

@Data
public class SecKillQueryDTO {
    private Integer currentPage;
    private Integer pageSize;
    private String key;
    private Integer status;
}
