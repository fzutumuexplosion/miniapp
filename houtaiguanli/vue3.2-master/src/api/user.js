// 发起请求得到用户数据
import request from './request'

export const getUser = (params) => {
  return request({
    url: '/users/1/5',
    method: 'GET',
    // headers: {
    //   'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    // },
    // data: decodeURI(params)
    params
  })
}

// export const getUserState = (uId, type) => {
//   return request({
//     url: `users/${uId}/state/${type}`,
//     method: 'PUT'
//   })
// }

// 添加用户
export const addUser = (data) => {
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

export const editUser = (data) => {
  return request({
    url: '/users/2',
    method: 'put',
    data
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
