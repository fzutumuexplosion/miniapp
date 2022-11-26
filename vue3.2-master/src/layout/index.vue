<!-- layout布局 -->
<template>
  <el-container class="app-wrapper">
    <el-aside :width="asideWidth" class="sidebar-container">
      <!-- 使用菜单 -->
      <Menu />
    </el-aside>
    <el-container
      class="container"
      :class="{ hidderContainer: !store.getters.sideBarType }"
    >
      <el-header><Header /></el-header>
      <el-main>
        <!-- 与路径最后对应 -->
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
// 导入菜单
import Menu from './Menu'
import Header from './header'
import { computed } from 'vue'
import { useStore } from 'vuex'
// import variables from '@/styles/variables.scss'
// 之所以注释掉 是本来想用上面scss文件里面的变量，但是这里用scss出现了错误，就先放置不用了。
const store = useStore()
const asideWidth = computed(() => {
  return store.getters.sideBarType ? '210px' : '67px'
})
</script>

<style lang="scss" scoped>
.app-container {
  position: relative;
  width: 100%;
  height: 100%;
}
.container {
  width: calc(100% - $sideBarWidth);
  height: 100%;

  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  transition: all 0.28s;
  &.hidderContainer {
    width: calc(100% - $hideSideBarWidth);
  }
}
:deep(.el-header) {
  padding: 0;
}
</style>
