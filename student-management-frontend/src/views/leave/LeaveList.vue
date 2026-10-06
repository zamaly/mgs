<template>
  <div>
    <el-card>
      <template #header>
        <span>请假审批</span>
      </template>

      <el-table :data="leaveList" border style="width: 100%" stripe>
        <el-table-column prop="studentId" label="学号" align="center" width="120"></el-table-column>
        <el-table-column prop="leaveType" label="请假类型" align="center" width="100">
          <template #default="scope">
            {{ scope.row.leaveType === 1 ? '事假' : scope.row.leaveType === 2 ? '病假' : '其他' }}
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" align="center" width="180">
          <template #default="scope">
            {{ scope.row.startTime ? new Date(scope.row.startTime).toLocaleString() : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="endTime" label="结束时间" align="center" width="180">
          <template #default="scope">
            {{ scope.row.endTime ? new Date(scope.row.endTime).toLocaleString() : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="reason" label="请假原因" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="状态" align="center" width="100">
          <template #default="scope">
            <el-tag
                :type="scope.row.status === 0 ? 'warning' : scope.row.status === 1 ? 'success' : 'danger'"
                size="small"
            >
              {{ scope.row.status === 0 ? '待审批' : scope.row.status === 1 ? '已通过' : '已拒绝' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="applyTime" label="申请时间" align="center" width="180">
          <template #default="scope">
            {{ scope.row.applyTime ? new Date(scope.row.applyTime).toLocaleString() : '-' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="180" v-if="isAdmin">
          <template #default="scope">
            <el-button
                type="success"
                size="small"
                @click="handleApprove(scope.row.leaveId)"
                :disabled="scope.row.status !== 0"
            >
              通过
            </el-button>
            <el-button
                type="danger"
                size="small"
                @click="handleReject(scope.row.leaveId)"
                :disabled="scope.row.status !== 0"
                style="margin-left: 5px"
            >
              拒绝
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'

// 请假列表数据
const leaveList = ref([])
// 是否为管理员（true显示审批按钮，false只显示列表）
const isAdmin = ref(true)

// 获取请假列表
const getLeaveList = () => {
  axios.get('/leave/list').then(res => {
    if (res.data.code === 200) {
      leaveList.value = res.data.data
    } else {
      ElMessage.error(res.data.message || '获取请假列表失败')
    }
  }).catch(err => {
    ElMessage.error('网络错误，请检查后端是否启动')
    console.error(err)
  })
}

// 审批通过
const handleApprove = (leaveId) => {
  ElMessageBox.confirm('确定要通过这条请假申请吗？', '确认审批', {
    confirmButtonText: '确定通过',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    axios.put(`/leave/approve/${leaveId}`).then(res => {
      if (res.data.code === 200) {
        ElMessage.success('审批通过成功')
        getLeaveList() // 刷新列表
      } else {
        ElMessage.error(res.data.message || '审批失败')
      }
    }).catch(err => {
      ElMessage.error('网络错误，请检查后端是否启动')
      console.error(err)
    })
  })
}

// 审批拒绝
const handleReject = (leaveId) => {
  ElMessageBox.confirm('确定要拒绝这条请假申请吗？', '确认拒绝', {
    confirmButtonText: '确定拒绝',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    axios.put(`/leave/reject/${leaveId}`).then(res => {
      if (res.data.code === 200) {
        ElMessage.success('已拒绝该请假申请')
        getLeaveList() // 刷新列表
      } else {
        ElMessage.error(res.data.message || '操作失败')
      }
    }).catch(err => {
      ElMessage.error('网络错误，请检查后端是否启动')
      console.error(err)
    })
  })
}

// 页面加载时获取数据
onMounted(() => {
  getLeaveList()
})
</script>