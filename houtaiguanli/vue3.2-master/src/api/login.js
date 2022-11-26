// 发起请求拿到登录数据
import request from './request'

// 形参data
export const login = (data) => {
  return request({
    // 地址
    url: '/administrator/login',
    // 方法
    method: 'POST',
    // 接受用户传过来的参数
    // headers: {
    //   'Content-Type': 'application/json'
    // },
    data
  })
}
