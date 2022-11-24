import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login/index.vue')
  },
  {
    path: '/',
    name: '/',
    component: () => import('../layout'),
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import('@/views/home/index.vue')
      },
      {
        path: 'users',
        name: 'users',
        component: () => import('@/views/users/index.vue')
      },
      {
        path: 'history',
        name: 'history',
        component: () => import('@/views/history/index.vue')
      },
      {
        path: 'picture',
        name: 'picture',
        component: () => import('@/views/picture/index.vue')
      },
      {
        path: 'schedule',
        name: 'schedule',
        component: () => import('@/views/schedule/index.vue')
      },
      {
        path: 'sendword',
        name: 'sendword',
        component: () => import('@/views/sendword/index.vue')
      },
      {
        path: 'class',
        name: 'class',
        component: () => import('@/views/class/index.vue')
      },
      {
        path: 'classdetails',
        name: 'classdetails',
        component: () => import('@/views/class/classdetails.vue')
      },
      {
        path: 'lottery',
        name: 'lottery',
        component: () => import('@/views/lottery/index.vue')
      },
      {
        path: 'lotterylist',
        name: 'lotterylist',
        component: () => import('@/views/lottery/lotterylist.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
