// 发起请求得到用户数据
import request from './request'

export const getClassDetails = (params) => {
  return request({
    url: '/prizeDetail/1/5',
    method: 'GET',
    params
  })
}
