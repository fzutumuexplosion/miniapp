// 发起请求得到用户数据
import request from './request'

export const getLottery = (params) => {
  return request({
    url: '/prize/1/5',
    method: 'GET',
    params
  })
}

// // 添加用户
export const addLottery = (data) => {
  return request({
    url: '/prize',
    method: 'post',
    data
  })
}

export const editLottery = (data) => {
  return request({
    url: `/prize/${data.id}`,
    method: 'put',
    data
  })
}

export const deleteLottery = (id) => {
  return request({
    url: `/prize/${id}`,
    method: 'delete'
  })
}
export const delAllLottery = () => {
  return request({
    url: '/prize',
    method: 'delete'
  })
}
