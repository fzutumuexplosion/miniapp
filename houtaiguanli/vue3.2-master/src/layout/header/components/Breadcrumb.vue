<!-- 头部显示所在位置 -->

<template>
  <el-icon color="#666"><Operation /></el-icon>
  <!-- separator是以什么分割的意思 -->
  <el-breadcrumb separator="/">
    <el-breadcrumb-item v-for="(item, index) in breadList" :key="index">
      <!-- 最后一项 -->
      <span class="no-redirect" v-if="index === breadList.length - 1">{{
        $t(`menus.${item.name}`)
      }}</span>
      <!-- 非最后一项 -->
      <!-- <span class="redirect" v-else @click="handleRedirect(item.path)">{{
        $t(`menus.${item.name}`)
      }}</span> -->
      <span class="redirect" v-else >{{
        $t(`menus.${item.name}`)
      }}</span>
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script setup>
// 导入用于获取到当前路由的所有路由表
import { useRoute } from 'vue-router'
// 监听：每次点击的时候更新路由表
import { ref, watch } from 'vue'

const route = useRoute()
// const router = useRouter()
const breadList = ref([])
const initbreadList = () => {
  // route.matched获取到当前路由的所有路由表
  breadList.value = route.matched
  // console.log(route.matched)
}

// 点击时进行跳转
// const handleRedirect = (path) => {
//   router.push(path)
// }

watch(
  route,
  () => {
    // 当接受到新值时
    initbreadList()
  },
  {
    deep: true,
    immediate: true
  }
)
</script>

<style lang="scss" scoped>
.no-redirect {
  // 鼠标移上去的时候是一个文本的样子
  cursor: text;
  color: #304156;
}
.redirect {
  color: #666;
  cursor: text;
  // 鼠标移上去的时候是一个小手
  // cursor: pointer;
  &.active {
    color: yellow;
  }
}
</style>
