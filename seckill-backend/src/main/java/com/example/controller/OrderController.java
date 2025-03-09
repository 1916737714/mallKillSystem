package com.example.controller;
import com.example.pojo.OrderItem;
import com.example.pojo.Orders;
import com.example.pojo.OrdersVO;
import com.example.service.OrdersService;
import com.example.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrdersService ordersService;
    @PostMapping("/add")
    public Result<OrdersVO> addOrder(@Validated @RequestBody List<OrderItem> items) {
        OrdersVO order=ordersService.addOrder(items);
        return Result.success("生成订单成功",order);
    }

    @PostMapping("/addSecKill")
    public Result<OrdersVO> addSecKill(@NotNull Integer secKillId, @NotNull @Min(1) Integer goodsNum){
        OrdersVO order=ordersService.addSecKillOrder(secKillId,goodsNum);
        return Result.success("生成订单成功",order);
    }

    @DeleteMapping("/delete/{orderId}")
    public Result deleteOrders(@PathVariable Integer orderId) {
        ordersService.deleteorders(orderId);
        return Result.success("删除订单成功");
    }
    @PutMapping("/cancel")
    public Result cancel(@NotNull Integer orderId){
        ordersService.cancel(orderId);
        return Result.success("取消订单成功");
    }
    @PutMapping("/pay")
    public Result pay(@NotNull Integer orderId){
        ordersService.pay(orderId);
        return Result.success("支付成功");
    }

    @GetMapping("/searchOrdersByUserId/{userId}")
    public Result<List<OrdersVO>> searchOrdersByUserId(@PathVariable Integer userId) {
        List<OrdersVO> orders = ordersService.findAllOrdersByUserId(userId);
        return Result.success("查询订单成功", orders);
    }

    @GetMapping("/detail/{orderId}")
    public Result<OrdersVO> getOrderDetail(@PathVariable Integer orderId){
        OrdersVO order=ordersService.getOrderDetail(orderId);
        return Result.success("获取订单详情成功",order);
    }
}
