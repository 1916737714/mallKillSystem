import request from '@/utils/request'

export const addToCartService = (cartData) => {
    const params=new URLSearchParams()
    for(let key in cartData){
        params.append(key,cartData[key])
    }
    return request.post('/cart',params)
}

export const removeFromCartService = (cartId) => {
    return request.delete(`/cart/delete/${cartId}`)
}

export const getCartListService = (userId) => {
    return request.get(`/cart/searchGoodsInfo/${userId}`)
}

export const updateNumService = (cartData) => {
    const params=new URLSearchParams()
    for(let key in cartData){
        params.append(key,cartData[key])
    }
    return request.post('/cart/update',params)
}
