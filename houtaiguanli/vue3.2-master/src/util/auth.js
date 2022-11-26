// 定义好的有效期时间，用户登录时候，设置当前时间，进行时间上的比较，看是否过期。
import { TOKEN_TIME, TOKEN_TIME_VALUE } from './constant'

// 登录时设置时间
export const setTokenTime = () => {
  localStorage.setItem(TOKEN_TIME, Date.now())
}

// 获取
export const getTokenTime = () => {
  return localStorage.getItem(TOKEN_TIME)
}

// 是否已经过期
export const diffTokenTime = () => {
  const currentTime = Date.now()
  const tokenTime = getTokenTime()
  return currentTime - tokenTime > TOKEN_TIME_VALUE
}
