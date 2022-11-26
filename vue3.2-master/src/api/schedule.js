// 发起请求得到用户数据
import request from './request'

export const getSchedule = (params) => {
  return request({
    url: '/schedule/1',
    method: 'GET',
    // headers: {
    //   'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    // },
    // data: decodeURI(params)
    params
  })
}

export const editSchedule = (data) => {
  return request({
    url: '/schedule/2',
    method: 'put',
    data
  })
}

export const deleteSchedule = (id) => {
  return request({
    url: `schedule/${id}`,
    method: 'delete'
  })
}

export const deleteAllSchedule = () => {
  return request({
    url: '/schedule',
    method: 'delete'
  })
}
