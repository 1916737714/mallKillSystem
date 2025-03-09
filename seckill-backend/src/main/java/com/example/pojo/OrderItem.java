package com.example.pojo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;
    @NotNull
    private Integer goodsId;
    private String description;
    private String unit;
    private BigDecimal price;
    private String picture;
    private Integer stock;
    @NotNull
    @Min(1)
    private Integer goodsNum;
    private BigDecimal payPrice;
    private Integer isSecKill;
}
