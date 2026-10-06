<template>
  <div>
    <el-card>
      <template #header>
        <span>申请请假</span>
      </template>

      <el-form
          :model="leaveForm"
          label-width="100px"
          style="max-width: 600px; margin: 0 auto"
          ref="leaveFormRef"
      >
        <el-form-item
            label="请假类型"
            prop="leaveType"
            :rules="[{ required: true, message: '请选择请假类型', trigger: 'change' }]"
        >
          <el-select v-model="leaveForm.leaveType" placeholder="请选择" style="width: 100%">
            <el-option label="事假" :value="1"></el-option>
            <el-option label="病假" :value="2"></el-option>
            <el-option label="其他" :value="3"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item
            label="开始时间"
            prop="startTime"
            :rules="[{ required: true, message: '请选择开始时间', trigger: 'change' }]"
        >
          <el-date-picker
              v-model="leaveForm.startTime"
              type="datetime"
              placeholder="选择日期和时间"
              style="width: 100%"
          ></el-date-picker>
        </el-form-item>

        <el-form-item
            label="结束时间"
            prop="endTime"
            :rules="[{ required: true, message: '请选择结束时间', trigger: 'change' }]"
        >
          <el-date-picker
              v-model="leaveForm.endTime"
              type="datetime"
              placeholder="选择日期和时间"
              style="width: 100%"
          ></el-date-picker>
        </el-form-item>

        <el-form-item
            label="请假原因"
            prop="reason"
            :rules="[{ required: true, message: '请输入请假原因', trigger: 'blur' }]"
        >
          <el-input
              v-model="leaveForm.reason"
              type="textarea"
              rows="4"
              placeholder="请输入请假原因"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" style="width: 120px">提交申请</el-button>
          <el-button @click="handleReset" style="margin-left: 10px">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

// 表单引用（纯JavaScript写法，去掉TypeScript类型）
const leaveFormRef = ref()

// 表单数据
const leaveForm = reactive({
  leaveType: null,
  startTime: '',
  endTime: '',
  reason: ''
})

// 提交申请
const handleSubmit = () => {
  leaveFormRef.value.validate((valid) => {
    if (valid) {
      axios.post('/leave/apply', leaveForm).then(res => {
        if (res.data.code === 200) {
          ElMessage.success('申请提交成功')
          handleReset()
        } else {
          ElMessage.error(res.data.message || '提交失败')
        }
      }).catch(err => {
        ElMessage.error('网络错误，请检查后端是否启动')
        console.error(err)
      })
    }
  })
}

// 重置表单
const handleReset = () => {
  leaveFormRef.value.resetFields()
}
</script>