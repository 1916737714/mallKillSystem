package com.example.service;


import com.example.pojo.*;


public interface SecKillService {


    PageBean<SecKillVO> list(SecKillQueryDTO secKillQueryDTO);
//    PagBean<SecKillVO> list(BaseQueryDTO baseQueryDTO,Integer offset);

//    PageBean<SecKillVO> list(BaseQueryDTO baseQueryDTO,Integer offset);


    SecKillVO detail(Integer secKillId);

    PageBean<SecKillVO> listAll();


    void addSecKill(SecKill secKill);

    SecKill findById(Integer goodsId);

    void AlterSecKill(SecKill secKill);

    void deleteSecKill(Integer secKillId);

    void AlterSecKilling(Integer secKillId, Integer alterStock);

    void AlterSecKilled();

    PageBean<SecKill> findEndStock1(SecKillQueryDTO secKillQueryDTO);
}
