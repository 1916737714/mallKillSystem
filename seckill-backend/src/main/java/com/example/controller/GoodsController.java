package com.example.controller;

import com.example.pojo.BaseQueryDTO;
import com.example.pojo.Goods;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.service.GoodsService;
import com.example.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
     GoodsService goodsService;

    //录入商品
    @PostMapping
    public Result add(@Validated Goods goods,@RequestParam("file") MultipartFile file){
        goodsService.add(goods,file);
        return Result.success("添加成功");
    }

    //删除商品
    @DeleteMapping("/delete/{goodsId}")
    public Result delete(@PathVariable Integer goodsId){
        // TODO 查询秒杀活动中是否有该商品
        Goods goods=goodsService.findGoodsById(goodsId);
        UploadUtil.deleteImage(goods.getPicture());
        goodsService.delete(goodsId);
        return Result.success("删除成功");
    }
    //查询商品分页以及模糊匹配
    @GetMapping("/searchAllLimit")
    public Result searchAll(BaseQueryDTO baseQueryDTO) {
        PageBean<Goods> goods = goodsService.searchAllLimit(baseQueryDTO);
        return Result.success("查询成功", goods);
    }
    //查询所有商品
    @GetMapping("/findall")
    public Result findAll() {
        List<Goods> goods = goodsService.findAllGoods();
        return Result.success("查询成功", goods);
    }

    //查询单个商品详情
    @GetMapping("/searchInfo/{goodsId}")
    public Result searchInfo(@PathVariable Integer goodsId){
        Goods goods=  goodsService.findGoodsById(goodsId);
        return Result.success("查询商品详情成功",goods);
    }

    //修改商品
    @PostMapping("/update")
    public Result update(@Validated Goods goods,@RequestParam(value = "file",required = false) MultipartFile file){
        goodsService.update(goods,file);
        return Result.success("修改成功");
    }

}
