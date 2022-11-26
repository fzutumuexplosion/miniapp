// 得到了登录成功之后返回的数据后
// 需要把数据当中的token 存储在vuex中和localStorage中

// login as loginApi因为与下面的login()函数冲突，设置别名为loginApi
import { login as loginApi } from '@/api/login'
// 导入路由，用于登录成功之后跳到首页
import router from '@/router'
// 导入用于设置登录时间，下面记得执行
import { setTokenTime } from '@/util/auth'

export default {
  namespaced: true,
  state: {
    token: localStorage.getItem('token') || '',
    sideBarType: true,
    lang: localStorage.getItem('lang') || 'zh'
  },
  mutations: {
    setToken(state, token) {
      state.token = token
      localStorage.setItem('token', token)
    },
    changeSideBarType(state) {
      state.sideBarType = !state.sideBarType
    },
    changeLang(state, lang) {
      state.lang = lang
    }
  },
  actions: {
    // token 来自登录页面 点击登录之后，所以不如直接放到actions中
    // { commit }, userInfo接受一个用户信息
    login({ commit }, userInfo) {
      // return发起请求
      return new Promise((resolve, reject) => {
        loginApi(userInfo)
          .then((res) => {
            console.log(res)
            commit('setToken', res)
            setTokenTime()
            // 到首页
            router.replace('/')
            resolve()
          })
          .catch((err) => {
            // 失败
            reject(err)
          })
      })
    },
    // 登录过期后被动退出操作
    logout({ commit }) {
      // 把存的值设置为一个空字符串
      commit('setToken', '')
      localStorage.clear()
      router.replace('/login')
    }
  }
}
