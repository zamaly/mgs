<template>
  <el-container style="height: 100vh">
    <el-aside width="200px" style="background-color: #304156">
      <el-menu
          router
          background-color="#304156"
          text-color="#fff"
          active-text-color="#409EFF"
          default-active="$route.path"
      >
        <el-menu-item index="/home/studentList">
          <span>学生管理</span>
        </el-menu-item>
        <el-sub-menu index="leave">
          <template #title>请假管理</template>
          <el-menu-item index="/home/leaveApply">申请请假</el-menu-item>
          <el-menu-item index="/home/leaveList">请假审批</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/home/logList">日志管理
        </el-menu-item>

      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background-color: #fff; display: flex; justify-content: space-between; align-items: center; padding: 0 20px">
        <span>学生管理系统</span>
        <el-button type="text" @click="handleLogout">退出登录</el-button>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

const handleLogout = () => {
  sessionStorage.removeItem('loginUser')
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>