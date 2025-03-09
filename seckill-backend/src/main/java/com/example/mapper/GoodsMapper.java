package com.example.mapper;

import com.example.pojo.Goods;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Insert("insert into goods values(null,#{description},#{unit},#{picture},#{price},#{stock})")
    void add(Goods goods);

    @Select("select * from goods where goods_id=#{goodsId}")
    Goods findGoodsById(Integer goodsId);

    @Delete("delete from goods where goods_id=#{goodsId}")
    void delete(Integer goodsId);

    Page<Goods> searchAllLimit(String key);

    void update(Goods goods);

    @Select("SELECT * FROM goods")
    List<Goods> findAllGoods();


//    @Update("update goods set stock = stock + #{Stock} where goods_id = #{goodsId}")
//    void updateStock(Integer goodsId, Integer Stock);

    @Update("update goods set stock=#{stock} where goods_id=#{goodsId}")
    void updateStock(Integer goodsId,int stock);
}
