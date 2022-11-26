// 路由守卫
// 常用场景：必须登录后才能访问首页

// 导入路由
import router from './index'
// token存在vuex里，导入store
import store from '@/store'

// 白名单，让用户没有登录的时候也可以去访问的页面
const whiteList = ['/login']
// router.beforeEach((to, from, next)
router.beforeEach((to, from, next) => {
  // 如果token存在
  if (store.getters.token) {
    // 要去的路径是login吗
    if (to.path === '/login') {
      // 已经登录了不需要再登录，直接到首页去
      next('/')
    } else {
      // 随便去
      next()
    }
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }
  }
})
