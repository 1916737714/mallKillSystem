package com.example.controller;

import com.example.pojo.Cart;
import com.example.pojo.Result;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    //添加商品入购物车
    @PostMapping()
    public Result addCart(@Validated Cart cart) {
        cartService.addcart(cart);
        return Result.success("添加购物车成功");
    }

    //删除购物车商品
    @DeleteMapping("/delete/{cartId}")
    public Result deleteCart(@PathVariable Integer cartId) {
        cartService.deletecart(cartId);
        return Result.success("删除购物车内商品成功");
    }

    //查询购物车所有商品信息
    @GetMapping("/searchGoodsInfo/{userId}")
    public Result searchGoodsInfo(@PathVariable Integer userId) {
        List<Cart> goodsList = cartService.findCartByUserId(userId);
        return Result.success("查询商品成功", goodsList);
    }

    //更新购物车商品数量
    @PostMapping("/update")
    public Result updateCart(@Validated Cart cart){
        cartService.updatecart(cart);
        return Result.success("更新购物车商品数量成功");
    }

}
