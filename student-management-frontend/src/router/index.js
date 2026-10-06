import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import StudentList from '../views/student/StudentList.vue'
import LeaveApply from '../views/leave/LeaveApply.vue'
import LeaveList from '../views/leave/LeaveList.vue'
// 导入日志页面
import LogList from '../views/log/LogList.vue'

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/home',
        name: 'Home',
        component: Home,
        children: [
            {
                path: 'studentList',
                name: 'StudentList',
                component: StudentList
            },
            {
                path: 'leaveApply',
                name: 'LeaveApply',
                component: LeaveApply
            },
            {
                path: 'leaveList',
                name: 'LeaveList',
                component: LeaveList
            },
            // 👇 在这里加日志管理路由
            {
                path: 'logList',
                name: 'LogList',
                component: LogList
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const loginUser = sessionStorage.getItem('loginUser')
    if (to.path === '/login') {
        next()
    } else {
        loginUser ? next() : next('/login')
    }
})

export default router