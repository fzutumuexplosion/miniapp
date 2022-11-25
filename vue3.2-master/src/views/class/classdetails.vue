<template>
    <!-- <span>{{ $route.params.id }}</span> -->
    <el-card>
      <!-- gutter间距 -->
      <el-row :gutter="20" class="header">
        <!-- span层数 -->
        <el-col :span="5">
          <!-- 搜索框 -->
          <!-- placeholder是搜索框默认的内容 -->
          <el-input
            placeholder="请输入搜索的用户学号"
            clearable
            type="text"
            v-model="queryForm.studentNum"
          ></el-input>
        </el-col>
        <el-col :span="5">
          <!-- 搜索框 -->
          <el-input
            placeholder="请输入搜索的用户姓名"
            clearable
            type="text"
            v-model="queryForm.studentName"
          ></el-input>
        </el-col>
        <!-- 搜索按钮 -->
        <el-button type="primary" :icon="Search" @click="initGetClassDetailsList">{{
          $t('table.search')
        }}</el-button>
        <!-- 添加用户 -->
        <el-button type="primary" @click="goBack()">返回</el-button>
      </el-row>

      <el-table :data="tableData" stripe style="width: 100%">
        <!-- 用户数据 -->
        <!-- 序号 -->
        <el-table-column type="index" label="序号" width="100" align="center" :index="indexMethod" />
        <!-- 其他数据 -->
        <el-table-column
          v-for="(item, index) in options2"
          :width="item.width"
          :prop="item.prop"
          :label="item.label"
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
              type="danger"
              size="small"
              :icon="Delete"
              @click="delClassDetails(row)"
            >踢出班级</el-button>
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
        @initUserList="initGetClassDetailsList"
      />
    </el-card>
  </template>

  <script setup>
  // 搜素图标
  import { Search, Delete } from '@element-plus/icons-vue'
  import { getClassDetails, deleteClassDetails } from '@/api/classDetails.js'
  import { ref } from 'vue'
  // 导入用户数据
  import { options2 } from './options2'
  // 消息提示，如添加成功之类
  import { ElMessage, ElMessageBox } from 'element-plus'
  // import { useI18n } from 'vue-i18n'
  // import isNull from '@/util/filters'
  import router from '@/router'
  const id = ref()
  // 得到用户数据
  const initGetClassDetailsList = async () => {
    id.value = router.currentRoute.value.params.id
    // console.log(1234)
    const res = await getClassDetails(id)
    // console.log(res)
    total.value = res.total
    // size.value = res.size
    // console.log(res.size)
    tableData.value = res.records
    console.log(res.records)
  }
  // console.log(router.currentRoute.value.params.id)
  initGetClassDetailsList()
  const indexMethod = (index) => {
    return index + 1
  }
  // const i18n = useI18n()
  const queryForm = ref({
    // 数据库定义的名字
    studentName: '',
    studentNum: '',
    // 当前页
    current: 1,
    // 每页显示条数
    size: 5
  })
  const total = ref(0)
  const tableData = ref([])
  // 页码改变
  const handleSizeChange = (Size) => {
    queryForm.value.current = 1
    queryForm.value.size = Size
    initGetClassDetailsList()
  }

  const handleCurrentChange = (Current) => {
    queryForm.value.current = Current
    initGetClassDetailsList()
  }
  // 删除
  const delClassDetails = (row) => {
    ElMessageBox.confirm('确定将该用户踢出', 'Warning', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        // console.log(row)
        await deleteClassDetails(row.openId)
        console.log(row.openId)
        ElMessage({
          type: 'success',
          message: '删除成功'
        })
        initGetClassDetailsList()
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除'
        })
      })
  }

  // 返回
const goBack = () => {
  router.replace('/class')
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
