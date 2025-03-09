package com.example.pojo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class Goods {
    private Integer goodsId;
    @NotEmpty
    private String description;
    @NotEmpty
    private String unit;
    @DecimalMin("0")
    private BigDecimal price;
    private String picture;
    @NotNull
    private Integer stock;
}
