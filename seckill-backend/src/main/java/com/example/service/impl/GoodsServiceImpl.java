package com.example.service.impl;

import com.example.mapper.GoodsMapper;
import com.example.pojo.BaseQueryDTO;
import com.example.pojo.Goods;
import com.example.pojo.PageBean;
import com.example.service.GoodsService;
import com.example.utils.UploadUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Override
    public void add(Goods goods, MultipartFile file) {
        String url=UploadUtil.uploadImage(file);
        goods.setPicture(url);
        goodsMapper.add(goods);
    }

    @Override
    public List<Goods> findAllGoods() {
        return goodsMapper.findAllGoods();
    }

    @Override
    public Goods findGoodsById(Integer goodsId) {
        Goods goods=goodsMapper.findGoodsById(goodsId);
        return goods;
    }

    @Override
    public void delete(Integer goodsId) {
        goodsMapper.delete(goodsId);
    }

    @Override
    public PageBean<Goods> searchAllLimit(BaseQueryDTO baseQueryDTO) {
        PageHelper.startPage(baseQueryDTO.getCurrentPage(), baseQueryDTO.getPageSize());
        Page<Goods> page=goodsMapper.searchAllLimit(baseQueryDTO.getKey());
        return new PageBean<>(page.getTotal(),page.getResult());
    }

    @Override
    public void update( Goods goods, MultipartFile file) {

        if (file!=null && goods.getPicture()!=null){
            UploadUtil.deleteImage(goods.getPicture());
            String url=UploadUtil.uploadImage(file);
            goods.setPicture(url);
            goodsMapper.update(goods);
        }else if (file!=null){
            String url=UploadUtil.uploadImage(file);
            goods.setPicture(url);
            goodsMapper.update(goods);
        }else{
            goodsMapper.update(goods);
        }

    }

//    @Override
//    public PageBean<Goods> list(BaseQueryDTO baseQueryDTO) {
//        PageHelper.startPage(baseQueryDTO.getCurrentPage(),baseQueryDTO.getPageSize());
//        Page<Goods> goods=goodsMapper.list(baseQueryDTO.getKey());
//        return new PageBean<>(goods.getTotal(),goods.getResult());
//    }
}
