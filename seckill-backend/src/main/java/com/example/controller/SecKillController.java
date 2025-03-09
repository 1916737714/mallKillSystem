package com.example.controller;


import com.example.pojo.*;
import com.example.service.GoodsService;
import com.example.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/secKill")
public class SecKillController {
    @Autowired
    SecKillService secKillService;

    @Autowired
    GoodsService goodsService;

    //分页获取秒杀商品列表
    @GetMapping("/list")

    public Result<PageBean<SecKillVO>> list(SecKillQueryDTO secKillQueryDTO){
//        Integer offset = (baseQueryDTO.getCurrentPage() - 1) * baseQueryDTO.getPageSize();
        System.out.println("key="+secKillQueryDTO.getKey());
        PageBean<SecKillVO> secKillPagBean=secKillService.list(secKillQueryDTO);
        return Result.success("成功获取",secKillPagBean);

    }


    //获取秒杀商品详情
    @GetMapping("/detail")
    public Result<SecKillVO> detail(@NotNull Integer secKillId){
        System.out.println("secId="+secKillId);
        SecKillVO secKillvo=secKillService.detail(secKillId);
        System.out.println("sec="+secKillvo);
        return Result.success("获取详情成功",secKillvo);
    }


    //新增秒杀活动
    @PostMapping("/create")
    public Result CreateSecKill(@Validated @RequestBody SecKill secKill){
        secKillService.addSecKill(secKill);
        return Result.success("创建秒杀活动成功");
    }

    //修改未开始的秒杀活动
    @PutMapping("/alter")
    public Result AlterSecKill(@RequestBody SecKill secKill){
//    public Result AlterSecKill(@Validated @RequestBody SecKill secKill){
        secKillService.AlterSecKill(secKill);
        return Result.success("修改秒杀活动成功");
    }

    //修改进行中的秒杀活动库存
    @PutMapping("/altering")
    public Result AlterSecKilling(@NotNull Integer secKillId,@NotNull Integer alterStock){
        secKillService.AlterSecKilling(secKillId,alterStock);
        return Result.success("修改秒杀库存成功");
    }

    //修改已经结束的秒杀活动
    @PutMapping("/altered")
    public Result AlterSecKilled(){
        secKillService.AlterSecKilled();
        return Result.success("修改秒杀库存成功，把已经结束的还有剩余的秒杀库存加回到商品库存中");
    }

    //返回秒杀结束并秒杀库存不为0的秒杀活动
    @GetMapping("/findEndStock")
    public Result<PageBean<SecKill>> findEndStock1(SecKillQueryDTO secKillQueryDTO){
//        System.out.println("key="+secKillQueryDTO.getKey());
        PageBean<SecKill> secKillPagBean=secKillService.findEndStock1(secKillQueryDTO);
        return Result.success("成功获取",secKillPagBean);
    }

    //删除秒杀活动
    @DeleteMapping("/delete")
    public Result DeleteSecKill(@NotNull Integer secKillId){
        secKillService.deleteSecKill(secKillId);
        return Result.success("删除秒杀活动成功");
    }

}
