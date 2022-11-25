<template>
  <el-card>
    <!-- gutter间距 -->
    <el-row :gutter="20" class="header">
      <!-- span层数 -->
      <el-col :span="5">
        <!-- 搜索框 -->
        <!-- placeholder是搜索框默认的内容 -->
        <el-input
          placeholder="请输入搜索的用户年级"
          clearable
          type="text"
          v-model="queryForm.grade"
        ></el-input>
      </el-col>
      <el-col :span="5">
        <!-- 搜索框 -->
        <el-input
          placeholder="请输入搜索的用户专业"
          clearable
          type="text"
          v-model="queryForm.profession"
        ></el-input>
      </el-col>
      <el-col :span="5">
        <!-- 搜索框 -->
        <el-input
          placeholder="请输入搜索的用户班级"
          clearable
          type="text"
          v-model="queryForm.className"
        ></el-input>
      </el-col>
      <!-- 搜索按钮 -->
      <el-button type="primary" :icon="Search" @click="initGetClassList">{{
        $t('table.search')
      }}</el-button>
      <!-- 添加用户 -->
      <el-button type="primary" @click="handleDialogValue()">添加班级</el-button>
    </el-row>

    <el-table :data="tableData.slice((queryForm.current - 1)*queryForm.size,queryForm.current*queryForm.size)" stripe style="width: 100%">
      <!-- 用户数据 -->
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
        <!-- 开关 -->
        <!-- <template v-slot="{ row }" v-if="item.prop === 'mg_state'"
          ><el-switch v-model="row.mg_state" @change="changeUserState(row)" />
        </template> -->

        <!-- 创建时间 -->
        <!-- <template v-slot="{ row }" v-if="item.prop === 'create_time'"
          >{{ $filters.filterTimes(row.create_time) }}
        </template> -->

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
            @click="handleClass(row)"
          >查看详细</el-button>
          <el-button
            type="danger"
            size="small"
            :icon="Delete"
            @click="delClass(row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器 -->
    <el-pagination
      v-model:currentPage="queryForm.current"
      :page-size="queryForm.size"
      layout="prev, pager, next, jumper"
      :total="tableData.length"
      @current-change="handleCurrentChange"
      @initUserList="initGetClassList"
    />
  </el-card>

  <!-- 使用弹窗框时要记得绑定v-model="dialogVisible" -->
  <!-- v-if="dialogVisible"每次刷新再次添加用户时框里为空值，而不是上一次的值 -->
  <Dialog
    v-model="dialogVisible"
    :dialogTitle="dialogTitle"
    v-if="dialogVisible"
    :dialogTableVal="dialogTableVal"
    @initUserList = "initGetClassList"
  />
</template>

<script setup>
// 搜素图标
import { Search, ChatLineSquare, Edit, Delete } from '@element-plus/icons-vue'
import { getClass, deleteClass } from '@/api/class.js'
import { ref } from 'vue'
// 导入用户数据
import { options } from './options'
// 消息提示，如添加成功之类
import { ElMessage, ElMessageBox } from 'element-plus'
import { useI18n } from 'vue-i18n'
// 导入弹窗框
import Dialog from './component/dialog.vue'
import router from '@/router'
// import isNull from '@/util/filters'

const indexMethod = (index) => {
  return index + 1
}
const i18n = useI18n()
const queryForm = ref({
  // 数据库定义的名字
  grade: '',
  profession: '',
  className: '',
  // 当前页
  current: 1,
  // 每页显示条数
  size: 3
})
const total = ref(0)
const tableData = ref([])
// 对话框默认值为false表示隐藏
const dialogVisible = ref(false)
const dialogTitle = ref('')
const dialogTableVal = ref({})

// 得到用户数据
const initGetClassList = async () => {
  const res = await getClass(queryForm.value)
  // console.log(res)
  total.value = res.total
  // size.value = res.size
  // console.log(res.size)
  tableData.value = res.records
  // console.log(res.records)
}
initGetClassList()

const handleCurrentChange = (Current) => {
  queryForm.value.current = Current
  initGetClassList()
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
    dialogTitle.value = '添加班级'
    dialogTableVal.value = {}
  } else {
    dialogTitle.value = '修改'
    dialogTableVal.value = JSON.parse(JSON.stringify(row))
  }
  dialogVisible.value = true
}

const handleClass = (row) => {
  console.log(row.id)
  router.push({ name: 'classdetails', params: { id: row.id } })
}
// 删除一行
const delClass = (row) => {
  ElMessageBox.confirm(i18n.t('dialog.deleteTitle'), 'Warning', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      await deleteClass(row.id)
      console.log(row.id)
      ElMessage({
        type: 'success',
        message: '删除成功'
      })
      initGetClassList()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除'
      })
    })
}
</script>

<style lang="scss" scoped>
.el-button.el-button--danger.el-button--small{
  margin-left: 20px;
}
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
