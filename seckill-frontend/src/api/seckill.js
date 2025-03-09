import request from '@/utils/request'
//admin
export const addSecKillService = (secKill) => {
    return request.post('/secKill/create',secKill)
}

export const delSecKillService = (secKillId) => {
    return request.delete('/secKill/delete',{
        params:{secKillId}
    })
}

export const getSeKillListService = (query) => {
    return request.get('/secKill/list',{
        params:query
    })
}

export const updateSecKillService = (secKill) => {
    return request.put('/secKill/alter',secKill)
}

export const updateSecKillIngService = (secKillId,stock) => {
    const params=new URLSearchParams()
    params.append("secKillId",secKillId)
    params.append("alterStock",stock)
    return request.put('/secKill/altering',params)
}

export const updateEndStockService = () => {
    return request.put('/secKill/altered')
}

//user
export const getSecKillDetailService = (secKillId) => {
    return request.get('/secKill/detail',{
        params:{secKillId}
    })
}