package com.example.pojo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
public class Cart {
    private Integer cartId;
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotNull(message = "商品id不能为空")
    private Integer goodsId;
    @DecimalMin(value = "0",message = "商品数量不能小于0")
    private Integer goodsNum;

    private Goods goods;
}
