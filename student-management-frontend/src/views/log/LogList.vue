<template>
  <div>
    <el-card>
      <template #header>
        <span>系统日志管理</span>
        <el-button
            type="primary"
            style="float: right; margin-left: 10px"
            @click="handleExport"
        >
          导出Excel
        </el-button>
        <el-select
            v-model="logType"
            placeholder="全部类型"
            style="width: 120px; float: right"
            @change="getLogList"
            clearable
        >
          <el-option label="一般信息" :value="1"></el-option>
          <el-option label="错误信息" :value="2"></el-option>
        </el-select>
      </template>

      <el-table :data="logList" border style="width: 100%" stripe>
        <el-table-column prop="logId" label="日志ID" align="center" width="80"></el-table-column>
        <el-table-column prop="logType" label="日志类型" align="center" width="100">
          <template #default="scope">
            <el-tag
                :type="scope.row.logType === 1 ? 'success' : 'danger'"
                size="small"
            >
              {{ scope.row.logType === 1 ? '一般信息' : '错误信息' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="操作内容" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="operator" label="操作人" align="center" width="100"></el-table-column>
        <el-table-column prop="ipAddress" label="IP地址" align="center" width="150"></el-table-column>
        <el-table-column prop="exceptionMessage" label="异常信息" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="createTime" label="操作时间" align="center" width="180">
          <template #default="scope">
            {{ scope.row.createTime ? new Date(scope.row.createTime).toLocaleString() : '-' }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const logList = ref([])
const logType = ref(null)

// 获取日志列表
const getLogList = () => {
  axios.get('/log/list', {
    params: { logType: logType.value }
  }).then(res => {
    if (res.data.code === 200) {
      logList.value = res.data.data
    } else {
      ElMessage.error(res.data.message || '获取日志列表失败')
    }
  }).catch(err => {
    ElMessage.error('网络错误，请检查后端是否启动')
    console.error(err)
  })
}

// 导出Excel
const handleExport = () => {
  window.open(`/api/log/export?logType=${logType.value || ''}`)
}

onMounted(() => {
  getLogList()
})
</script>