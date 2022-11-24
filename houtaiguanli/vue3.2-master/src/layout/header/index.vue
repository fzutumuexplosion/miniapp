<template>
  <div class="navbar">
    <Breadcrumb />
    <div class="navbar-right">
      <Driver class="navbar-item" />

      <Screenfull class="navbar-item" />
      <Lang class="navbar-item" />
      <Avatar class="navbar-item" />
    </div>
  </div>
</template>

<script setup>
// 导入面包屑
import Breadcrumb from './components/Breadcrumb.vue'
</script>

<style lang="scss" scoped>
.navbar {
  width: 100%;
  height: 60px;
  overflow: hidden;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  padding: 0 16px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  position: relative;
}
</style>
