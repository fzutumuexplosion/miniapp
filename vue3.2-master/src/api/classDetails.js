// 发起请求得到用户数据
import request from './request'

export const getClassDetails = (params) => {
  return request({
    url: '/class/86/1/5',
    method: 'GET',
    params
  })
}

export const deleteClassDetails = (id) => {
  return request({
    url: `classDetails/${id}`,
    method: 'delete'
  })
}
