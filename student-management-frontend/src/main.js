import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'

const app = createApp(App)

// 配置axios，对接你的Java后端地址
axios.defaults.baseURL = 'http://localhost:8080/api'
axios.defaults.withCredentials = true
app.config.globalProperties.$http = axios

app.use(ElementPlus)
app.use(router)
app.mount('#app')