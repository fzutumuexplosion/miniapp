// 发起请求得到用户数据
import request from './request'

export const getSendword = (params) => {
  return request({
    url: '/message/1/5',
    method: 'GET',
    params
  })
}

// 添加
export const addSendword = (data) => {
  return request({
    url: '/messages',
    method: 'post',
    data
  })
}

export const editSendword = (data) => {
  return request({
    url: `messages/${data.id}`,
    method: 'put',
    data
  })
}

export const deleteSendword = (id) => {
  return request({
    url: `messages/${id}`,
    method: 'delete'
  })
}
export const delAllSendword = () => {
  return request({
    url: '/messages',
    method: 'delete'
  })
}
