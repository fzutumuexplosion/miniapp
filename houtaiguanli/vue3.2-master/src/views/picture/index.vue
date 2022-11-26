<template>
  <el-card>
    <!-- gutter间距 -->
    <el-row :gutter="20" class="header">
      <!-- span层数 -->
      <el-col :span="5">
        <!-- 下拉栏 -->
        <el-select v-model="queryForm.posotion" clearable placeholder="选择所属地">
        <el-option
          v-for="item in PositionData"
          :key="item.label"
          :label="item.label"
          :value="item.position"
        />
      </el-select>
      </el-col>
      <el-button type="primary" @click="handleDialogValue()" >添加</el-button>
    </el-row>
    <el-row>
    <el-col
      v-for="(item, index) in PositionData"
      :key="index"
      :span="8"
    >
      <el-card>
       <img
          :src="item.path"
          class="image"
        />
        <div style="padding: 10px">
          <div class="title">{{ item.name }}</div>
          <el-button type="primary" class="button" @click="delPicture(item)">删除</el-button>
        </div>
      </el-card>
    </el-col>
  </el-row>
</el-card>
<Dialog v-model="dialogVisible"/>
</template>

<script setup>
// 搜素图标
// import { Search, Delete, Edit } from '@element-plus/icons-vue'
// import { getUser, deleteUser } from '@/api/picture.js'
import { getPicture, deletePicture } from '@/api/picture.js'
import { ref } from 'vue'
// 导入用户数据
// import { options } from './options'
// 消息提示，如添加成功之类
import { ElMessage, ElMessageBox } from 'element-plus'
// import { useI18n } from 'vue-i18n'
// 导入弹窗框
import Dialog from './component/dialog.vue'
// import { request } from 'http'
// import { method } from 'lodash'
// import isNull from '@/util/filters'
// const i18n = useI18n()
const queryForm = ref({
  // 数据库定义的名字
  query: '',
  // 当前页
  current: '',
  size: ''
})
// const total = ref(0)]
const PositionData = ref([])

const dialogVisible = ref(false)

// 得到用户数据
const initGetPicture = async () => {
  const res = await getPicture(queryForm.value)
  // console.log(res)
  // 地点信息
  PositionData.value = res.records
}
initGetPicture()

const handleDialogValue = () => {
  dialogVisible.value = true
}
// const currentDate = ref(new Date())
const delPicture = (item) => {
  console.log(item)
  ElMessageBox.confirm(
    '确认删除?',
    '提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    }
  )
    .then(async() => {
      await deletePicture(item.id)
      console.log(item.id)
      ElMessage({
        type: 'success',
        message: '删除成功'
      })
      initGetPicture()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除'
      })
    })
}

</script>

<style>
.el-row.header{
  margin-left: 40px !important;
}

.el-main{
  padding:0px;
}

.el-col-8 {
    max-width: 19%;
    margin-left: 5.1%;
    margin-top: 2%;
    flex: 0 0 33.3333333333%;
}

.title{
  font-size: 15px;
  font-weight: bold;
  text-align: center;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  min-height: auto;
  margin-left: 75%;
}

.image {
  width: 100%;
  display: block;
}
</style>
