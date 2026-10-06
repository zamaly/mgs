<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <div class="card-header">学生学籍管理系统</div>
      </template>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名/学号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" style="width: 100%">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const router = useRouter()
const loginFormRef = ref()
const loginForm = reactive({
  username: '',
  password: ''
})

const rules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      // 关键修改：改成params传参，而不是body
      axios.post('/user/login', null, {
        params: {
          username: loginForm.username,
          password: loginForm.password
        }
      }).then(res => {
        if (res.data.code === 200) {
          sessionStorage.setItem('loginUser', JSON.stringify(res.data.data))
          ElMessage.success('登录成功')
          router.push('/home/studentList')
        } else {
          ElMessage.error(res.data.message || '登录失败')
        }
      }).catch(err => {
        ElMessage.error('网络错误，请检查后端是否启动')
        console.error(err)
      })
    }
  })
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}
.login-card {
  width: 400px;
}
.card-header {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
}
</style>