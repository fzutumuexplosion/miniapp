<!-- 添加用户弹窗框 -->
<template>
  <!-- 根据dialogVisible这个值决定是否隐藏，在父的index.vue中定义 -->
  <el-dialog
    :model-value="dialogVisible"
    width="40%"
    @close="handleClose"
  >
    <!-- 表单内容 -->
    <el-form :model="form" label-width="100px" ref="formRef" :rules="rules">
      <el-form-item label="奖品内容" prop="content">
        <el-input v-model="form.content" />
      </el-form-item>
      <el-form-item label="奖品等级" prop="level">
        <el-input v-model="form.level" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <!-- 取消按钮 -->
        <el-button @click="handleClose">取消</el-button>
        <!-- 确认按钮 -->
        <el-button type="primary" @click="handleConfirm">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
// 因为要定义值所以要导入ref
import { defineEmits, ref, defineProps, watch } from 'vue'
import { editLottery, addLottery } from '@/api/lottery'
import { ElMessage } from 'element-plus'
import i18n from '@/i18n'
const emits = defineEmits(['update:modelValue', 'initUserList'])
const formRef = ref(null)
// 数据源
const form = ref({
  content: '',
  level: ''
})
const props = defineProps({
  dialogTableVal: {
    type: Object,
    // default: {} 此处会报错 因为vue规定，对象或数组默认值必须从一个工厂函数获取
    default: function () {
      return {}
    }
  }
})

// 监听改变
watch(
  () => props.dialogTableVal,
  () => {
    form.value = props.dialogTableVal
    // console.log(props.dialogTableVal)
  },
  { deep: true, immediate: true }
)

// 关闭弹窗
const handleClose = () => {
  emits('update:modelValue', false)
}

// 确认事件
const handleConfirm = () => {
  console.log(form.value)
  // 表单统一验证
  formRef.value.validate(async (valid) => {
    if (valid) {
      if (props.dialogTitle === '添加') {
        await addLottery(form.value)
        ElMessage({
          message: i18n.global.t('message.addSuccess'),
          type: 'success'
        })
      } else {
        await editLottery(form.value)
        ElMessage({
          message: i18n.global.t('message.editSuccess'),
          type: 'success'
        })
      }
      // 喷发事件:实现确认后自己更新到页面上，不需要刷新后才更新显示
      emits('initUserList')
      // 关闭弹窗
      handleClose()
    } else {
      console.log('error submit!!')
      return false
    }
  })
}

const rules = ref({
  content: [
    {
      required: true,
      message: 'Please select Activity zone',
      trigger: 'blur'
    }
  ],
  level: [
    {
      required: true,
      message: 'Please select Activity zone',
      trigger: 'blur'
    }
  ]
})
</script>

<style lang="scss" scoped>
.details{
  font-size: 20px;
}
// .el-dialog__body{
//   margin-top: 10px;
// }
</style>
