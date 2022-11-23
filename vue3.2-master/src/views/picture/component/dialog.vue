<!-- 添加用户弹窗框 -->
<template>
<el-dialog
    :model-value="dialogVisible"
    title="添加合影背景"
    width="30%"
    @close="handleClose"
  >
    <el-select v-model="queryForm.position" clearable placeholder="选择所属地">
      <el-option
        v-for="item in PositionData"
        :key="item.value"
        :label="item.label"
        :value="item.position"
      />
    </el-select>
    <!-- 添加内容 -->
    <el-upload
      class="upload-demo"
      drag
      action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
    >
      <el-icon class="el-icon--upload"><upload-filled /></el-icon>
      <div class="el-upload__text">
      拖拽或 <em>选择文件</em>
      </div>
      <template #tip>
      <div class="el-upload__tip">
          小于500kb的 jpg / png 文件
      </div>
      </template>
    </el-upload>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import { defineEmits, ref } from 'vue'
import { getPicture } from '@/api/picture.js'
const queryForm = ref({
  // 数据库定义的名字
  position: '',
  // 当前页
  current: '',
  size: ''
})
const PositionData = ref([])
const initGetPicture = async () => {
  const res = await getPicture(queryForm.value)
  console.log(res)
  PositionData.value = res.records
}
initGetPicture()
const emits = defineEmits(['update:modelValue'])
// 添加取消
const handleClose = () => {
    emits('update:modelValue', false)
}
// 添加确认
const handleConfirm = () => {
    handleClose()
}
</script>

<style lang="scss" scoped></style>
