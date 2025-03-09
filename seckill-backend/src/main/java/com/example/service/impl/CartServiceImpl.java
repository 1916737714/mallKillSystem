package com.example.service.impl;


import com.example.mapper.CartMapper;
import com.example.mapper.GoodsMapper;
import com.example.pojo.Cart;
import com.example.pojo.Goods;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;


    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public void addcart(Cart cart){
        cartMapper.addcart(cart);
    }

    @Override
    public void deletecart(Integer cartId){
        cartMapper.deletecart(cartId);
    }

    @Override
    public void updatecart(Cart cart){
        cartMapper.updatecart(cart);
    }

    @Override
    public List<Cart> findCartByUserId(Integer userId){
        // 获取用户的购物车列表
        List<Cart> cartList = cartMapper.findCartByUserId(userId);

        // 创建一个 Map 用于存储合并后的购物车项，key 为 goodsId
        Map<Integer, Cart> mergedCartMap = new HashMap<>();

        // 遍历购物车列表，合并相同 goodsId 的项
        for (Cart cart : cartList) {
            Integer goodsId = cart.getGoodsId();
            if (goodsId != null) {
                if (mergedCartMap.containsKey(goodsId)) {
                    // 如果已经存在相同的 goodsId，则相加数量
                    Cart existingCart = mergedCartMap.get(goodsId);
                    existingCart.setGoodsNum(existingCart.getGoodsNum() + cart.getGoodsNum());
                } else {
                    // 否则添加到 map 中
                    mergedCartMap.put(goodsId, cart);
                }
            }
        }

        // 填充商品信息到购物车对象
        for (Cart cart : mergedCartMap.values()) {
            Integer goodsId = cart.getGoodsId();
            if (goodsId != null) {
                Goods goods = goodsMapper.findGoodsById(goodsId);  // 使用单个商品查询方法
                cart.setGoods(goods);  // 填充商品信息
            }
        }

        // 将合并后的购物车项转换为列表返回
        return new ArrayList<>(mergedCartMap.values());
    }


}
