package com.example.mapper;

import com.example.pojo.OrderItem;
import com.example.pojo.Orders;
import com.example.pojo.OrdersVO;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface OrdersMapper {
    @Insert("insert into orders values(null,#{number},#{userId},#{totalPrice},#{status},#{orderTime},#{payTime})")
    void addorders(Orders orders);

    @Delete("delete from orders where order_id = #{orderId}")
    void deleteorders(Integer orderId);

    @Select("select * from orders where user_id = #{userId} order by order_time desc")
    List<OrdersVO> findAllOrdersByUserId(Integer userId);

    @Update("update orders set status = #{status},pay_time = #{payTime} where order_id = #{orderId}")
    void updateorders(Orders orders);

    @Select("select * from orders where number=#{number}")
    Orders findOrderByNumber(String number);

    void addOrderItem(List<OrderItem> items, Integer orderId);
    @Select("select * from order_item o " +
            "join goods g on g.goods_id=o.goods_id " +
            "where order_id=#{orderId}")
    List<OrderItem> findOrderItemsByOrderId(Integer orderId);
    @Select("select * from orders where order_id=#{orderId}")
    Orders findOrderByOrderId(Integer orderId);

    @Select("select count(goods_num) from order_item " +
            " where is_sec_kill=#{secKillId} and order_id in " +
            "(select order_id from orders where user_id=#{userId} and status=2)")
    int getBoughtNum(Integer userId, Integer secKillId);
}
