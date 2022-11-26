// 用于发起登录请求
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { diffTokenTime } from '@/util/auth'
// 把退出操作写在vuex里面，所以要导入store
import store from '@/store'

// 发起登录请求
const service = axios.create({
  // 基础路径：根据开发环境.env.development
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时时间
  timeout: 5000
})

// 为每一个接口添加上token信息
service.interceptors.request.use(
  (config) => {
    // 登录后有token，判断是否有token
    if (localStorage.getItem('token')) {
      // 为true表示以及过期
      if (diffTokenTime()) {
        // 通过vuex实现退出：在app下定义一下方法
        store.dispatch('app/logout')
        return Promise.reject(new Error('token 失效了'))
      }
    }
    config.headers.Authorization = localStorage.getItem('token')
    return config
  },
  (error) => {
    console.log('token错误')
    return Promise.reject(new Error(error))
  }
)

// 响应拦截器：用于获取登录后的数据
service.interceptors.response.use(
  // 有响应
  (response) => {
    // response即为登录后的data,响应拦截器就是来获取data里面的数据
    // console.log(response)
    const { data } = response.data
    // console.log(response.data)
    const status = response.status
    if (status === 200) {
      // 成功，返回想要的数据
      return data
    } else {
      // 失败，来自element-plus，记得导入ElMessage
      ElMessage.error('密码错误')
      // 返回一个错误信息
      return Promise.reject(new Error('密码错误'))
    }
    // return data
  },
  // 没有响应时
  (error) => {
    console.log('没有响应')
    error.response && ElMessage.error(error.response.data)
    return Promise.reject(new Error(error.response.data))
  }
)

export default service
