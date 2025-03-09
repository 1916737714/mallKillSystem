package com.example.service.impl;

import com.example.exception.BusinessException;
import com.example.mapper.GoodsMapper;
import com.example.mapper.OrdersMapper;
import com.example.mapper.SecKillMapper;
import com.example.pojo.*;
import com.example.service.OrdersService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    SecKillMapper secKillMapper;

    @Transactional
    @Override
    public OrdersVO addOrder(List<OrderItem> items) {
        BigDecimal totalPrice= new BigDecimal(0);
        for(OrderItem item:items){
            Goods goods=goodsMapper.findGoodsById(item.getGoodsId()); //检查库存
            if(goods.getStock()<item.getGoodsNum()){
                throw new BusinessException("库存不足");
            }
            BigDecimal goodsNum=new BigDecimal(item.getGoodsNum());
            totalPrice=totalPrice.add(item.getPayPrice().multiply(goodsNum)); //计算总计
        }
        Orders saveOrder=genOrder(totalPrice);
        ordersMapper.addOrderItem(items,saveOrder.getOrderId()); //生成订单项
        for(OrderItem item:items){
            goodsMapper.updateStock(item.getGoodsId(), item.getStock()-item.getGoodsNum()); //更新库存
        }
        OrdersVO ordersVO=new OrdersVO();
        BeanUtils.copyProperties(saveOrder,ordersVO);
        List<OrderItem> orderItems=ordersMapper.findOrderItemsByOrderId(saveOrder.getOrderId());
        ordersVO.setOrderItems(orderItems);
        return ordersVO;
    }

    @Transactional
    @Override
    public OrdersVO addSecKillOrder(Integer secKillId, Integer goodsNum) {
        SecKillVO secKill = secKillMapper.detail(secKillId);
        //正在进行秒杀活动
        if(secKill!=null&&secKill.getStartTime().isBefore(LocalDateTime.now())
                &&secKill.getEndTime().isAfter(LocalDateTime.now())){
            Integer userId=ThreadLocalUtil.getUserId();
            if(goodsNum> secKill.getSecKillStock()) throw new BusinessException("库存不足");
            if(goodsNum> secKill.getLimitNum()) throw new BusinessException("选择的数量超过限购数");
            //查询该用户是否购买过该秒杀商品
            int boughtNum=ordersMapper.getBoughtNum(userId,secKillId); //查询购买次数
            int remainNum= secKill.getLimitNum() - boughtNum;
            if(boughtNum>= secKill.getLimitNum()||goodsNum>remainNum){
                throw new BusinessException("已购买"+boughtNum+"件该商品，还能购买"+remainNum+"件");
            }
            BigDecimal num=new BigDecimal(goodsNum);
            BigDecimal totalPrice=num.multiply(secKill.getSecKillPrice());
            Orders order=genOrder(totalPrice); //生成订单
            //组合订单项
            OrderItem item=new OrderItem();
            item.setOrderId(item.getOrderId());
            item.setGoodsId(secKill.getGoodsId());
            item.setGoodsNum(goodsNum);
            item.setPayPrice(secKill.getSecKillPrice());
            item.setIsSecKill(secKillId);
            List<OrderItem> items=new ArrayList<>();
            items.add(item);
            //生成订单项
            ordersMapper.addOrderItem(items,order.getOrderId());
            secKillMapper.alterSecKillStock(secKillId,secKill.getSecKillStock()-goodsNum);
            OrdersVO ordersVO=new OrdersVO();
            BeanUtils.copyProperties(order,ordersVO);
            List<OrderItem> orderItems=ordersMapper.findOrderItemsByOrderId(order.getOrderId());
            ordersVO.setOrderItems(orderItems);
            return ordersVO;
        }else{
            throw new BusinessException("该秒杀活动已结束");
        }
    }

    @Transactional
    public Orders genOrder(BigDecimal totalPrice){
        Orders orders=new Orders();
        String number=UUID.randomUUID().toString();
        orders.setNumber(number); //生成订单号
        orders.setOrderTime(LocalDateTime.now()); //设置下单时间
        orders.setStatus(1); //设置状态为已下单（1）
        orders.setUserId(ThreadLocalUtil.getUserId()); //ThreadLocal获取用户id
        orders.setTotalPrice(totalPrice);
        ordersMapper.addorders(orders); //生成订单
        Orders saveOrder=ordersMapper.findOrderByNumber(number);
        return saveOrder;
    }

    @Override
    public void deleteorders(Integer orderId) {
        ordersMapper.deleteorders(orderId);
    }

    @Override
    public List<OrdersVO> findAllOrdersByUserId(Integer userId) {
        List<OrdersVO> orders = ordersMapper.findAllOrdersByUserId(userId);
        for(OrdersVO ordersVO:orders){
            List<OrderItem> orderItems=ordersMapper.findOrderItemsByOrderId(ordersVO.getOrderId());
            ordersVO.setOrderItems(orderItems);
        }
        return orders;
    }

    @Transactional
    @Override
    public void cancel(Integer orderId) {
        Orders order = ordersMapper.findOrderByOrderId(orderId);
        if(order.getStatus()!=1) throw new BusinessException("当前订单无法取消");
        order.setStatus(3);
        ordersMapper.updateorders(order);
        //根据orderId查订单项
        List<OrderItem> orderItems=ordersMapper.findOrderItemsByOrderId(orderId);
        for(OrderItem orderItem:orderItems){
            if(orderItem.getIsSecKill()!=null){ //若为秒杀商品
                SecKillVO secKillVO=secKillMapper.detail(orderItem.getIsSecKill());
                SecKill secKill=new SecKill();
                BeanUtils.copyProperties(secKillVO,secKill);
                if(secKill.getStartTime().isBefore(LocalDateTime.now())
                        && secKill.getEndTime().isAfter(LocalDateTime.now())){
                    //正在进行秒杀活动，将数量加回秒杀活动库存中
                    secKillMapper.alterSecKillStock(secKill.getSecKillId(),secKill.getSecKillStock()+ orderItem.getGoodsNum());
                }else{
                    //秒杀活动已结束，将数量加回商品库存中
                    goodsMapper.updateStock(orderItem.getGoodsId(), orderItem.getStock()+orderItem.getGoodsNum());
                }
            } else{
                //为普通商品，直接加回商品库存中
                goodsMapper.updateStock(orderItem.getGoodsId(), orderItem.getStock()+orderItem.getGoodsNum());
            }
        }
    }

    @Override
    public void pay(Integer orderId) {
        Orders order = ordersMapper.findOrderByOrderId(orderId);
        if(order.getStatus()==1){
            order.setStatus(2);
            order.setPayTime(LocalDateTime.now());
            ordersMapper.updateorders(order);
        }
    }

    @Override
    public OrdersVO getOrderDetail(Integer orderId) {
        Orders order=ordersMapper.findOrderByOrderId(orderId);
        if(order==null){
            throw new BusinessException("订单不存在");
        }
        List<OrderItem> orderItems=ordersMapper.findOrderItemsByOrderId(orderId);
        OrdersVO ordersVO=new OrdersVO();
        BeanUtils.copyProperties(order,ordersVO);
        ordersVO.setOrderItems(orderItems);
        return ordersVO;
    }
}
