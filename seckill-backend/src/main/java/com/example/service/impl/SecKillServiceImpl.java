package com.example.service.impl;

import com.example.exception.BusinessException;
import com.example.mapper.GoodsMapper;
import com.example.mapper.SecKillMapper;

import com.example.pojo.*;

import com.example.pojo.BaseQueryDTO;
import com.example.pojo.PageBean;
import com.example.pojo.SecKillVO;
import com.example.pojo.SecKill;

import com.example.service.SecKillService;
import com.example.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    SecKillMapper secKillMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    UserService userService;

    @Override
    public PageBean<SecKillVO> list(SecKillQueryDTO secKillQueryDTO) {

        User u=userService.findUserById();
        System.out.println("role="+u.getRole());
        PageHelper.startPage(secKillQueryDTO.getCurrentPage(),secKillQueryDTO.getPageSize());
        Page<SecKillVO> page=secKillMapper.list(secKillQueryDTO.getKey(),secKillQueryDTO.getStatus(),u.getRole());
        return new PageBean<>(page.getTotal(),  page.getResult());
    }

    @Override
    public SecKillVO detail(Integer secKillId) {
        SecKillVO secKillVO=secKillMapper.detail(secKillId);
        return secKillVO;
    }

    @Override
    public PageBean<SecKillVO> listAll() {
        return null;
    }

    @Override
    public void addSecKill(SecKill secKill) {

        Goods goods=goodsMapper.findGoodsById(secKill.getGoodsId());
//        SecKill secKill1=secKillMapper.findById(goods.getGoodsId());
        //要先查此商品的秒杀活动有没有在进行中或未开始的，新创建的秒杀活动时间不能有重叠的
        if(goods.getPrice().compareTo(secKill.getSecKillPrice())<0){
            throw new BusinessException("秒杀价格比原价高");
        }
        if(secKill.getStartTime().isBefore(LocalDateTime.now())){
            throw new BusinessException("不能创建早于当前时间的秒杀活动");
        }
        if(secKill.getEndTime().isBefore(secKill.getStartTime())){
            throw new BusinessException("结束时间不能早于开始时间");
        }
        if(isTimeOverlap(secKill)){
            throw new BusinessException("新秒杀活动的时间与已有活动重叠");
        }
        if(secKill.getSecKillStock()>goods.getStock()){
            throw new BusinessException("秒杀库存不能大于商品库存");
        }
        if(secKill.getLimitNum()>secKill.getSecKillStock()){
            throw new BusinessException("限购数不能大于商品库存");
        }
        secKillMapper.addSecKill(secKill);
        Integer stock=goods.getStock()-secKill.getSecKillStock();
        goodsMapper.updateStock(secKill.getGoodsId(),stock);
    }

    @Override
    public SecKill findById(Integer goodsId) {
        return secKillMapper.findById(goodsId);
    }

    @Override
    public void AlterSecKill(SecKill secKill) {
//        SecKillVO secKillVO=secKillMapper.detail(secKill.getSecKillId());
        SecKill sec=secKillMapper.findSecKillById(secKill.getSecKillId());
        Goods goods=goodsMapper.findGoodsById(sec.getGoodsId());
        Integer status=SecKillStatus(sec);

        if(status!=1){
            throw new BusinessException("不是未开始状态，不可以修改秒杀活动");
        }
        System.out.println("秒杀活动未开始");
        if(secKillMapper.findById(secKill.getGoodsId())==null){
            throw new BusinessException("没有此商品的秒杀活动");
        }
        if(goods.getPrice().compareTo(secKill.getSecKillPrice())<0){
            throw new BusinessException("秒杀价格比原价高");
        }
        if(secKill.getStartTime().isBefore(LocalDateTime.now())){
            throw new BusinessException("修改的开始时间不能早于当前时间的秒杀活动");
        }
        if(isTimeOverlap(secKill)){
            throw new BusinessException("修改秒杀活动的时间不能与已有活动重叠");
        }
        //如果需要修改未开始秒杀活动的库存，那么需要先的秒杀库存加回到商品库存里，再重新改
        if(sec.getSecKillStock()!= secKill.getSecKillStock()){
            goodsMapper.updateStock(sec.getGoodsId(),sec.getSecKillStock()+goods.getStock());
        }
        if(secKill.getSecKillStock()>goods.getStock()){
            throw new BusinessException("秒杀库存不能大于商品库存");
        }
        if(secKill.getLimitNum()>secKill.getSecKillStock()){
            throw new BusinessException("限购数不能大于商品库存");
        }
        secKillMapper.alterSecKill(secKill);
        //重新获取商品信息，再减掉商品库存
        goods=goodsMapper.findGoodsById(sec.getGoodsId());
        Integer stock=goods.getStock()-secKill.getSecKillStock();
        goodsMapper.updateStock(secKill.getGoodsId(),stock);
    }

    //判断秒杀活动状态
    public Integer SecKillStatus(SecKill secKill){
        LocalDateTime now=LocalDateTime.now();
        Integer status=3;
        if(now.isBefore(secKill.getStartTime())) status= 1;
        if(now.isAfter(secKill.getEndTime())) status= 0;
        if(now.isBefore(secKill.getEndTime()) && now.isAfter(secKill.getStartTime()))status= 2;
        return status;
    }



    //判断新建的秒杀活动或修改秒杀活动时间时，是否与此商品的已有活动重叠
    public boolean isTimeOverlap(SecKill secKill){
        List<SecKill> overlappingSecKills=secKillMapper.findOverlappingSecKills(secKill);
        return !overlappingSecKills.isEmpty();
    }

    //删除秒杀活动
    @Override
    public void deleteSecKill(Integer secKillId) {
        if(SecKillStatus(secKillMapper.findSecKillById(secKillId))!=1){
            throw new BusinessException("秒杀活动不是未开始状态，不可以删除秒杀活动");
        }
        secKillMapper.deleteSecKill(secKillId);
    }

    //修改进行中的秒杀活动
    @Override
    public void AlterSecKilling(Integer secKillId, Integer alterStock) {
        System.out.println("秒杀活动进行中");
        System.out.println("secKillId="+secKillId);
        SecKill secKill=secKillMapper.findSecKillById(secKillId);
        System.out.println("secKill="+secKill);
        Goods goods=goodsMapper.findGoodsById(secKill.getGoodsId());
        Integer status=SecKillStatus(secKill);
        System.out.println("买多少件："+alterStock);
        if(alterStock<0 && alterStock+secKill.getSecKillStock()<0){
            throw new BusinessException("修改库存失败，原因需要减掉的库存大于现有秒杀库存");
        }
        if(alterStock>0 && alterStock>goods.getStock())
        {
            throw new BusinessException("修改库存失败，原因需要增加的库存大于现有商品库存");
        }
        if(status==2) {
            secKillMapper.alterSecKillStock(secKillId,alterStock+secKill.getSecKillStock());
            if(alterStock>0)goodsMapper.updateStock(goods.getGoodsId(),goods.getStock()-alterStock);
        }
    }

    @Override
    public void AlterSecKilled() {
        System.out.println("秒杀活动已经结束");
        List<SecKill> secKillList=secKillMapper.findEndStock();
        for(SecKill secKill:secKillList){
            Goods goods=goodsMapper.findGoodsById(secKill.getGoodsId());
            goodsMapper.updateStock(goods.getGoodsId(), goods.getStock()+secKill.getSecKillStock());
            secKillMapper.alterSecKillStock(secKill.getSecKillId(),0);
        }
    }

    @Override
    public PageBean<SecKill> findEndStock1(SecKillQueryDTO secKillQueryDTO) {
        PageHelper.startPage(secKillQueryDTO.getCurrentPage(),secKillQueryDTO.getPageSize());
        Page<SecKill> page=secKillMapper.findEndStock1();
        return new PageBean<>(page.getTotal(),  page.getResult());
    }
}
