<!-- 导航栏菜单 -->
<template>
  <!-- router用于路由跳转，以path路径进行路由跳转 -->
  <el-menu
    active-text-color="#ffd04b"
    background-color=rgb(47,65,89)
    class="el-menu-vertical-demo"
    :default-active="defaultActive"
    text-color="#fff"
    router
    unique-opened
    :collapse="!$store.getters.sideBarType"
  >
    <!-- 一级标题 -->
    <!-- <el-sub-menu
      :index="item.id"
      v-for="(item, index) in menusList"
      :key="item.id"
    >
      <template #title>
        <el-icon>
          <component :is="iconList[index]"></component>
        </el-icon>
        <span>{{ item.authName }}</span>
      </template> -->
      <!-- 二级标题 -->
      <!-- <el-menu-item
        :index="'/' + it.path"
        v-for="it in item.children"
        :key="it.id"
        @click="savePath(it.path)"
      >
        <template #title>
          <el-icon>
            <component :is="icon"></component>
          </el-icon>
          <span>{{ it.authName }}</span>
        </template>
      </el-menu-item>
    </el-sub-menu> -->

    <!-- 修改后 -->
    <el-menu-item
      v-for="item in itemlist"
      :index="'/'+item.path"
      :key="item.path"
      @click="savePath(item.path)"
    >
      <template #title>
        <el-icon>
          <component :is="item.icon"/>
        </el-icon>
        <span>{{ item.name }}</span>
      </template>
    </el-menu-item>

  </el-menu>
</template>

<script setup>
// 导入接口数据
import { menuList } from '@/api/menu'
import { ref } from 'vue'
// import variables from '@/styles/variables.scss'

// 修改后
const itemlist = ref([
  {
    path: 'home',
    name: '主页',
    icon: 'House'
  },
  {
    path: 'users',
    name: '用户管理',
    icon: 'User'
  },
  {
    path: 'history',
    name: '学院历史信息管理',
    icon: 'Star'
  },
  {
    path: 'picture',
    name: '合影背景管理',
    icon: 'Picture'
  },
  {
    path: 'schedule',
    name: '院庆日程管理',
    icon: 'Calendar'
  },
  {
    path: 'sendword',
    name: '寄语管理',
    icon: 'ChatDotRound'
  },
  {
    path: 'class',
    name: '班级管理',
    icon: 'School'
  },
  {
    path: 'lottery',
    name: '抽奖管理',
    icon: 'Present'
  }
  // , '班级管理', '背景管理', '用户管理', '用户管理', '用户管理', '用户管理', '用户管理'
])
// const iconList = ref(['user', 'setting', 'shop', 'tickets', 'pie-chart'])
// const icon = ref('menu')
const defaultActive = ref(sessionStorage.getItem('path') || '/home')
const menusList = ref([])
const initMenuList = async () => {
  menusList.value = await menuList()
}

initMenuList()

const savePath = (path) => {
  sessionStorage.setItem('path', `/${path}`)
}
</script>

<style lang="scss" scoped></style>
