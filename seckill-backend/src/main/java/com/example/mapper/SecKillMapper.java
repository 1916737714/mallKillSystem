package com.example.mapper;

import com.example.pojo.BaseQueryDTO;
import com.example.pojo.SecKill;
import com.example.pojo.SecKillVO;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.*;


import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
@Mapper
public interface SecKillMapper {

    Page<SecKillVO> list(String key,Integer status,Integer role);


    @Select("SELECT COUNT(*) FROM sec_kill s JOIN goods g ON s.goods_id = g.goods_id WHERE g.description LIKE CONCAT('%', #{key}, '%')")
    long count(BaseQueryDTO baseQueryDTO);

    //查询秒杀活动详情
    @Select("select g.*,s.* "+
            "from goods g "+
            "join sec_kill s on s.goods_id=g.goods_id "+
            "where sec_kill_id=#{secKillId}")
    SecKillVO detail(Integer secKillId);

//    //模糊查询
//    @Select("select g.*,s.* from goods g "+"join sec_kill s on s.goods_id=g.goods_id "+"where g.description like '%#{goodstext}%'")
//    SecKillVO findBydecription(String goodstext);

    //创建秒杀活动
    @Insert("insert into sec_kill(goods_id,sec_kill_price,start_time,end_time,limit_num,sec_kill_stock) "+
            "values(#{goodsId},#{secKillPrice},#{startTime},#{endTime},#{limitNum},#{secKillStock})")
    void addSecKill(SecKill secKill);

    //查询此商品的历史秒杀活动的最新一个秒杀活动
    @Select("select * from sec_kill where goods_id=#{goodsId} order by end_time desc limit 1")
    SecKill findById(Integer goodsId);

    //修改秒杀活动
    @Update("update sec_kill set "+
            "sec_kill_price=#{secKillPrice},start_time=#{startTime},end_time=#{endTime},limit_num=#{limitNum},sec_kill_stock=#{secKillStock} "+
            "where sec_kill_id=#{secKillId}")
    void alterSecKill(SecKill secKill);


    @Select("select g.*,s.*"+
            "from goods g"+
            "join sec_kill s on s.goods_id=g.goods_id")
    Page<SecKillVO> findAll();

    //查看有没有重叠的时间
    @Select("SELECT * FROM sec_kill WHERE goods_id = #{goodsId} AND sec_kill_id !=#{secKillId} AND start_time <= #{endTime} AND end_time >= #{startTime}")
    List<SecKill> findOverlappingSecKills(SecKill secKill);

    //查询已结束的秒杀活动并且库存不为0
    @Select("select * from sec_kill where end_time <=NOW() and sec_Kill_stock>0")
    List<SecKill> findEndStock();

    @Select("select * from sec_kill where end_time <=NOW() and sec_Kill_stock>0")
    Page<SecKill> findEndStock1();

    //通过秒杀ID查询秒杀活动
    @Select("select * from sec_kill where sec_kill_id=#{secKillId}")
    SecKill findSecKillById(Integer secKillId);

    @Delete("delete from sec_kill where sec_kill_id=#{secKillId}")
    void deleteSecKill(Integer secKillId);

    @Update("update sec_kill set sec_kill_stock= #{Stock} where sec_kill_id=#{secKillId}")
    void alterSecKillStock(Integer secKillId, Integer Stock);

}
