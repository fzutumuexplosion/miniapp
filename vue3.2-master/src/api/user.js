// 发起请求得到用户数据
import request from './request'

export const getUser = (params) => {
  return request({
    url: '/users/:current/:size',
    method: 'GET',
    params
  })
}

// 添加用户
export const addUser = (data) => {
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

export const editUser = (id) => {
  return request({
    url: `users/${id}`,
    method: 'put'
  })
}

export const deleteUser = (id) => {
  return request({
    url: `users/${id}`,
    method: 'delete'
  })
}

export const deleteAllUser = () => {
  return request({
    url: '/users',
    method: 'delete'
  })
}
