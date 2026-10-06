<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center">
          <span>学生列表</span>
          <div>
            <el-button type="primary" @click="handleAdd">新增学生</el-button>
            <el-button type="success" @click="handleImport">导入Excel</el-button>
            <el-button type="warning" @click="handleExport">导出Excel</el-button>
          </div>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :model="searchForm" inline style="margin-bottom: 20px">
        <el-form-item label="学号">
          <el-input v-model="searchForm.studentId" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="searchForm.realName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 学生表格 -->
      <el-table :data="studentList" border style="width: 100%">
        <el-table-column prop="studentId" label="学号" align="center"></el-table-column>
        <el-table-column prop="realName" label="姓名" align="center"></el-table-column>
        <el-table-column prop="gender" label="性别" align="center"></el-table-column>
        <el-table-column prop="className" label="班级" align="center"></el-table-column>
        <el-table-column prop="major" label="专业" align="center"></el-table-column>
        <el-table-column prop="phone" label="手机号" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.studentId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" title="学生信息" width="500px">
      <el-form :model="studentForm" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="studentForm.studentId"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="studentForm.realName"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="studentForm.gender" placeholder="请选择">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="className">
          <el-input v-model="studentForm.className"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="studentForm.major"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="studentForm.phone"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 导入弹窗 -->
    <el-dialog v-model="importDialogVisible" title="导入学生" width="400px">
      <el-upload
          action="http://localhost:8080/api/student/import"
          :headers="{ 'Authorization': 'Bearer ' + sessionStorage.getItem('token') }"
          :show-file-list="false"
          accept=".xlsx,.xls"
          :on-success="handleImportSuccess"
      >
        <el-button type="primary">点击上传Excel文件</el-button>
      </el-upload>
      <div style="margin-top: 10px; color: #666">
        提示：请下载模板后填写数据再上传
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'

const studentList = ref([])
const dialogVisible = ref(false)
const importDialogVisible = ref(false)
const isEdit = ref(false)

const searchForm = reactive({
  studentId: '',
  realName: ''
})

const studentForm = reactive({
  id: null,
  studentId: '',
  realName: '',
  gender: '',
  className: '',
  major: '',
  phone: ''
})

// 获取学生列表
const getStudentList = () => {
  axios.get('/student/list', { params: searchForm }).then(res => {
    if (res.data.code === 200) {
      studentList.value = res.data.data
    }
  })
}

// 搜索
const handleSearch = () => {
  getStudentList()
}

// 重置
const handleReset = () => {
  searchForm.studentId = ''
  searchForm.realName = ''
  getStudentList()
}

// 新增
const handleAdd = () => {
  isEdit.value = false
  studentForm.id = null
  studentForm.studentId = ''
  studentForm.realName = ''
  studentForm.gender = ''
  studentForm.className = ''
  studentForm.major = ''
  studentForm.phone = ''
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(studentForm, row)
  dialogVisible.value = true
}

// 提交
const handleSubmit = () => {
  if (isEdit.value) {
    // 编辑
    axios.put('/student/update', studentForm).then(res => {
      if (res.data.code === 200) {
        ElMessage.success('修改成功')
        dialogVisible.value = false
        getStudentList()
      } else {
        ElMessage.error(res.data.message || '修改失败')
      }
    })
  } else {
    // 新增
    axios.post('/student/add', studentForm).then(res => {
      if (res.data.code === 200) {
        ElMessage.success('新增成功')
        dialogVisible.value = false
        getStudentList()
      } else {
        ElMessage.error(res.data.message || '新增失败')
      }
    })
  }
}

// 删除
const handleDelete = (studentId) => {
  ElMessageBox.confirm('确定要删除这条记录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    axios.delete(`/student/delete/${studentId}`).then(res => {
      if (res.data.code === 200) {
        ElMessage.success('删除成功')
        getStudentList()
      } else {
        ElMessage.error(res.data.message || '删除失败')
      }
    })
  })
}

// 导入
const handleImport = () => {
  importDialogVisible.value = true
}

const handleImportSuccess = (res) => {
  if (res.code === 200) {
    ElMessage.success('导入成功')
    importDialogVisible.value = false
    getStudentList()
  } else {
    ElMessage.error(res.message || '导入失败')
  }
}

// 导出
const handleExport = () => {
  window.open('http://localhost:8080/api/student/export')
}

onMounted(() => {
  getStudentList()
})
</script>