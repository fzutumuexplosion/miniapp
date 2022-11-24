<template>
  <el-card>
    <!-- gutter间距 -->
    <el-row :gutter="20" class="header">
      <!-- span层数 -->
      <el-col :span="5">
        <!-- 搜索框 -->
        <!-- placeholder是搜索框默认的内容 -->
        <el-input
          placeholder="请输入搜索的奖品等级"
          clearable
          type="text"
          v-model="queryForm.level"
        ></el-input>
      </el-col>
      <el-col :span="5">
        <!-- 搜索框 -->
        <el-input
          placeholder="请输入搜索的奖品内容"
          clearable
          type="text"
          v-model="queryForm.content"
        ></el-input>
      </el-col>
      <!-- 搜索按钮 -->
      <el-button type="primary" :icon="Search" @click="initGetLotteryList">搜索</el-button>
      <!-- 添加用户 -->
      <el-button type="primary" @click="handleDialogValue()">添加</el-button>
      <!-- 多选 -->
      <el-button
        v-model="Visible"
        v-if="Visible"
        type="primary"
        @click="handleChoice()"
      >多选</el-button>
      <el-button
        v-model="unVisible"
        v-if="unVisible"
        type="danger"
        :icon="Delete"
        @click="deleteAllLottery(row)"
      >删除</el-button>
      <el-button v-model="unVisible" v-if="unVisible" @click="handleClose">取消</el-button>
    </el-row>

    <el-table :data="tableData" stripe style="width: 100%" @selection-change="handleSelectionChange">
      <!-- 用户数据 -->
      <!-- 多选框 -->
      <el-table-column
        v-model="unVisible"
        v-if="unVisible"
        type="selection"
      ></el-table-column>
      <!-- 序号 -->
      <el-table-column type="index" label="序号" width="100" align="center" :index="indexMethod" />
      <!-- 其他数据 -->
      <el-table-column
        v-for="(item, index) in options"
        :width="item.width"
        :prop="item.prop"
        :label="$t(`table.${item.label}`)"
        :key="index"
        align="center"
      >
        <!-- #default="{ row }"和 @click="handleDialogValue(row)"里面加row拿到这一行的数据 -->
        <template #default="{ row }" v-if="item.prop === 'action'">
          <!-- 两个按钮 -->
          <el-button
            type="primary"
            size="small"
            :icon="Edit"
            @click="handleDialogValue(row)"
          >修改</el-button>
          <el-button
            type="primary"
            size="small"
            :icon="ChatLineSquare"
            @click="handleLottery()"
          >中奖名单</el-button>
          <el-button
            type="danger"
            size="small"
            :icon="Delete"
            @click="delLottery(row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器 -->
    <el-pagination
      v-model:currentPage="queryForm.current"
      :page-size="queryForm.size"
      layout="prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      @initLotteryList="initGetLotteryList"
    />
  </el-card>

  <!-- 使用弹窗框时要记得绑定v-model="dialogVisible" -->
  <!-- v-if="dialogVisible"每次刷新再次添加用户时框里为空值，而不是上一次的值 -->
  <Dialog
    v-model="dialogVisible"
    v-if="dialogVisible"
    title="查看详情"
    :dialogTableVal="dialogTableVal"
    @initLotteryList = "initGetLotteryList"
  />
</template>

<script setup>
// 搜素图标
import { Search, ChatLineSquare, Delete, Edit } from '@element-plus/icons-vue'

import { getLottery, delAllLottery, deleteLottery } from '@/api/lottery.js'

import { ref } from 'vue'
// 导入用户数据
import { options } from './options'
// 消息提示，如添加成功之类
import { ElMessage, ElMessageBox, ElTable } from 'element-plus'

import { useI18n } from 'vue-i18n'

// 导入弹窗框
import Dialog from './component/dialog.vue'
import router from '@/router'
// import isNull from '@/util/filters'
const unVisible = ref(false)
const Visible = ref(true)
const handleChoice = () => {
  unVisible.value = true
  Visible.value = false
}
const indexMethod = (index) => {
  return index + 1
}

const i18n = useI18n()

const queryForm = ref({
  // 当前页
  current: 1,
  // 每页显示条数
  size: 5
})
const total = ref(0)
const tableData = ref([])
// 对话框默认值为false表示隐藏
const dialogVisible = ref(false)
const dialogTitle = ref('')
const dialogTableVal = ref({})
const btnChangeEnable = ref(true)

// 得到用户数据
const initGetLotteryList = async () => {
  const res = await getLottery(queryForm.value)
  console.log(res)
  total.value = res.total
  // size.value = res.size
  // console.log(res.size)
  tableData.value = res.records
  // console.log(res.records)
}
initGetLotteryList()
// 多选框选择的值
const multipleSelection = ref([])
// 选中时触发方法
const handleSelectionChange = (val) => {
  console.log(val)
  multipleSelection.value = val
  if (val === ' ') {
    btnChangeEnable.value = true
  } else btnChangeEnable.value = false
}
// 页码改变
const handleSizeChange = (Size) => {
  queryForm.value.current = 1
  queryForm.value.size = Size
  initGetLotteryList()
}

const handleCurrentChange = (Current) => {
  queryForm.value.current = Current
  initGetLotteryList()
}

// 因为莫名的报错 就直接先把filter.js的判空方法直接先粘贴过来了 然后就不报错了
// 之前是将文件导入用里面的isnull 但一直提示一个全局变量报错
const isNull = (obj) => {
  if (!obj) return true
  if (JSON.stringify(obj) === '{}') return true
  if (JSON.stringify(obj) === '[]') return true
}

// 更新是否显示弹窗框
const handleDialogValue = (row) => {
  if (isNull(row)) {
    dialogTitle.value = '添加'
    dialogTableVal.value = {}
  } else {
    dialogTitle.value = '修改'
    dialogTableVal.value = JSON.parse(JSON.stringify(row))
  }
  dialogVisible.value = true
}

const handleLottery = () => {
  router.replace('/lotterylist')
}
// 删除多选行
const deleteAllLottery = () => {
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
      // const id = tableData.value.map(item => item.id).join()
      // console.log(id)
      const ids = multipleSelection.value.map(item => item.id).join()
      await delAllLottery(ids)
      console.log(ids)
      ElMessage({
        type: 'success',
        message: '删除成功'
      })
      initGetLotteryList()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除'
      })
    })
}
// 删除一行

const delLottery = (row) => {
  ElMessageBox.confirm(i18n.t('dialog.deleteTitle'), 'Warning', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      await deleteLottery(row.id)
      console.log(row.id)
      ElMessage({
        type: 'success',
        message: '删除成功'
      })
      initGetLotteryList()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除'
      })
    })
}

// 取消显示多选
const handleClose = () => {
  Visible.value = true
  unVisible.value = false
}

</script>

<style lang="scss" scoped>
.el-button.el-button--primary{
  margin-left: 20px;
}
.header{
  padding-bottom: 16px;
  box-sizing: border-box;
}
.el-pagination {
  margin-top: 20px;
  // justify-content: center; //居中
  //float: left;居左
  float: right; //居右
}
::v-deep .el-input__suffix{
  align-items: center;
}
:deep(.el-pagination.is-background .el-pager li:not(.is-disabled).is-active) {
  background-color: #ff9800 !important; //修改默认的背景色
}
</style>
