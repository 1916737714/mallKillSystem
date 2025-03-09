import request from '@/utils/request'

export const addOrderService = (orderItems) => {
    return request.post('/orders/add',orderItems)
}

export const addSecKillOrderService=(secKillId,goodsNum)=>{
    const params=new URLSearchParams()
    params.append('secKillId',secKillId)
    params.append('goodsNum',goodsNum)
    return request.post('/orders/addSecKill/',params)
}

export const getOrdersService = (userId) => {
    return request.get(`/orders/searchOrdersByUserId/${userId}`)
}

export const cancelOrderService = (orderId) => {
    const params=new URLSearchParams()
    params.append('orderId',orderId)
    return request.put('/orders/cancel/',params)
}

export const payService = (orderId) => {
    const params=new URLSearchParams()
    params.append('orderId',orderId)
    return request.put('/orders/pay/',params)
}

export const getOrderDetailService=(orderId)=>{
    return request.get(`/orders/detail/${orderId}`)
}