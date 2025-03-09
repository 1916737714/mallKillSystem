import request from '@/utils/request'

export const getGoodsListService=(params)=>{
    return request.get('/goods/searchAllLimit',{
        params:params
    })
}

export const getGoodsDetailService=(goodsId)=>{
    return request.get(`/goods/searchInfo/${goodsId}`)
}

export const getAllGoods=()=>{
    return request.get('/goods/findall')
}