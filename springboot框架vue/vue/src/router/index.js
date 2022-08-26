import { createRouter, createWebHistory } from 'vue-router'
import  Layout  from '../layout/Layout.vue'
import register from "@/views/Register";
const routes = [
  {
    path: '/',
    name: 'Layout ',
    redirect: "/home",
    component: Layout,

    children: [
      {
        path: 'home',
        name: 'Home ',
        component: () => import('../views/HomeView.vue')
      },

      {
        path: '/book',
        name: 'Book',
        component: () => import('../views/Book.vue')

      } ,{
        path: '/news',
        name: 'News',

        component: () => import('../views/News.vue')

      },{
        path: '/communication',
        name: 'Communication',

        component: () => import('../views/Communication.vue')

      },{
        path: '/health',
        name: 'Health',

        component: () => import('../views/Health.vue')

      }
    ]
        // () => import('../layout/Layout.vue'),
    // children: [
    //   {
    //     path: '/home',
    //     name: 'Home ',
    //     component: () => import('../views/HomeView.vue')
    //   }
    // ]

  },
  {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/Login.vue')

  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue')

  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})


export default router
