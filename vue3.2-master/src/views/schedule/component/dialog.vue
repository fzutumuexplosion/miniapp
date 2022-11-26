<!-- 添加用户弹窗框 -->
<template>
    <!-- 根据dialogVisible这个值决定是否隐藏，在父的index.vue中定义 -->
    <el-dialog
      :model-value="dialogVisible"
      :title="dialogTitle"
      width="40%"
      @close="handleClose"
    >
      <!-- 表单内容 -->
      <el-form :model="form" label-width="100px" ref="formRef" :rules="rules">
        <el-form-item label="时间" prop="time">
          <el-input v-model="form.time" />
        </el-form-item>
        <el-form-item label="地点" prop="position">
          <el-input v-model="form.position" />
        </el-form-item>
        <el-form-item label="活动" prop="content">
          <el-input v-model="form.content" />
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
  // 导入添加用户
  import { addClass, editClass } from '@/api/class'
  import { ElMessage } from 'element-plus'
  import i18n from '@/i18n'
  const emits = defineEmits(['update:modelValue', 'initUserList'])
  const formRef = ref(null)
  // 数据源
  const form = ref({
    openId: '',
    wxUnionId: '',
    studentName: '',
    studentNum: '',
    classNum: '',
    introduction: ''
  })
  const props = defineProps({
    dialogTitle: {
      type: String,
      default: '',
      required: true
    },
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
        if (props.dialogTitle === '添加班级') {
          await addClass(form.value)
          ElMessage({
            message: i18n.global.t('message.addSuccess'),
            type: 'success'
          })
        } else {
          await editClass(form.value)
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
    time: [
      {
        required: true,
        message: 'Please select Activity zone',
        trigger: 'blur'
      }
    ],
    position: [
      {
        required: true,
        message: 'Please select Activity zone',
        trigger: 'blur'
      }
    ],
    content: [
      {
        required: true,
        message: 'Please select Activity zone',
        trigger: 'blur'
      }
    ]
  })
  </script>

  <style lang="scss" scoped></style>
