// 发起请求得到用户数据
import request from './request'

export const getClass = (params) => {
  return request({
    url: '/class/1/5',
    method: 'GET',
    params
  })
}

// 添加班级
export const addClass = (data) => {
  return request({
    url: '/class/1',
    method: 'post',
    data
  })
}

export const editClass = (data) => {
  return request({
    url: '/class/2',
    method: 'put',
    data
  })
}

export const deleteClass = (id) => {
  return request({
    url: `class/${id}`,
    method: 'delete'
  })
}
