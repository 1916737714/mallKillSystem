package com.example.service;

import com.example.pojo.BaseQueryDTO;
import com.example.pojo.Goods;
import com.example.pojo.PageBean;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsService {
    void add(Goods goods, MultipartFile file);

    Goods findGoodsById(Integer goodsId);

    void delete(Integer goodsId);

    PageBean<Goods> searchAllLimit(BaseQueryDTO baseQueryDTO);

    void update(Goods goods,MultipartFile file);

    List<Goods> findAllGoods();
}

