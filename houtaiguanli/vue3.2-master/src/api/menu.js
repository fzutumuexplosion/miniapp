// 发起请求拿到导航栏菜单数据
import request from './request'

export const menuList = () => {
  return request({
    // 接口文件中菜单的请求路径
    url: '/menus'
    // 因为是get请求不用再写了
  })
}
