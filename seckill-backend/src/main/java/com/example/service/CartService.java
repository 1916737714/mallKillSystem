package com.example.service;

import com.example.pojo.Cart;
import com.example.pojo.Goods;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CartService {

    void addcart(Cart cart);

    void deletecart(Integer cartId);

    void updatecart(Cart cart);

    List<Cart> findCartByUserId(Integer userId);

}
