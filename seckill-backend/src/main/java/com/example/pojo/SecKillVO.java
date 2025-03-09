package com.example.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

import java.time.LocalDateTime;

@Data
public class SecKillVO {

    private Integer secKillId;
    private BigDecimal secKillPrice;
    private Integer limitNum;
    private Integer secKillStock;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Integer goodsId;
    private String description;
    private BigDecimal price;
    private String picture;
    private String unit;

}
