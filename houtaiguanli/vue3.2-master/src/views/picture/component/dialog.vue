<!-- 添加合影弹窗框 -->
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
      multiple
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
import { addPicture, getPicture } from '@/api/picture.js'
import { ElMessage } from 'element-plus'
import i18n from '@/i18n'
const emits = defineEmits(['update:modelValue', 'initGetPicture'])
const formRef = ref(null)
const queryForm = ref({
  // 数据库定义的名字
  position: '',
  // 当前页
  current: '',
  size: ''
})
const form = ref({
  name: '',
  path: '',
  position: ''
})
const PositionData = ref([])
const initGetPicture = async () => {
  const res = await getPicture(queryForm.value)
  console.log(res)
  PositionData.value = res.records
}
initGetPicture()
// 添加取消
const handleClose = () => {
    emits('update:modelValue', false)
}
// 添加确认
const handleConfirm = () => {
  console.log(form.value)
  // 表单统一验证
  formRef.value.validate(async (valid) => {
    if (valid) {
        await addPicture(form.value)
        ElMessage({
          message: i18n.global.t('message.addSuccess'),
          type: 'success'
        })
      // 喷发事件:实现确认后自己更新到页面上，不需要刷新后才更新显示
      emits('initGetPicture')
      // 关闭弹窗
      handleClose()
    } else {
      console.log('error submit!!')
      return false
    }
  })
}
</script>

<style lang="scss" scoped></style>
