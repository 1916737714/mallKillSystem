package com.example.service;

import com.example.pojo.OrderItem;
import com.example.pojo.OrdersVO;

import java.util.List;

public interface OrdersService {

    OrdersVO addOrder(List<OrderItem> items);

    void deleteorders(Integer orderId);

    List<OrdersVO> findAllOrdersByUserId(Integer userId);
    
    void cancel(Integer orderId);

    void pay(Integer orderId);

    OrdersVO addSecKillOrder(Integer secKillId, Integer goodsNum);

    OrdersVO getOrderDetail(Integer orderId);
}
