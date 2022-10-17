import { createRouter, createWebHistory } from 'vue-router'
import Index from "../components/Index.vue";
import LoginForm from "../components/LoginForm.vue";
import Demo from "../components/Demo.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: LoginForm
    },
    {
      path: '/index',
      component: Index
    },
    {
      path: '/Demo',
      component: Demo
    }
  ]
})

export default router
