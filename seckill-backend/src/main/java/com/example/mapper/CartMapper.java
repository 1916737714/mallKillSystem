package com.example.mapper;
import com.example.pojo.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("insert into cart values(null,#{userId},#{goodsId},#{goodsNum})")
    void addcart(Cart cart);

    @Delete("delete from cart where cart_id = #{cartId}")
    void deletecart(Integer cartId);

    @Update("update cart set goods_num = #{goodsNum} where cart_id = #{cartId} and user_id = #{userId}")
    void updatecart(Cart cart);

    @Select("select * from cart where user_id = #{userId}")
    List<Cart> findCartByUserId(Integer userId);
}
