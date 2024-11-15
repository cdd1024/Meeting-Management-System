import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [

    {
      path: '/',
      name: 'Manager',
      component: () => import('@/views/Manager.vue'),
      redirect: '/home',
      children: [
        { path: 'home', name: 'Home', component: () => import('@/views/manager/Home.vue')},
        { path: 'basics', name: 'Basics', component: () => import('@/views/manager/Basics.vue')},
        { path: 'password', name: 'Password', component: () => import('@/views/manager/Password.vue')},
        { path: 'person', name: 'Person', component: () => import('@/views/manager/Person.vue')},
        { path: 'conference', name: 'Conference', component: () => import('@/views/manager/Conference.vue')},
      ]
    },
    {path: '/login', name: 'login', component: () => import('@/views/login.vue'),},
    {path:'/register',name:'register',component:()=>import ('@/views/register.vue'),},
    {path:'/sidentify',name:'Sidentify',component:()=>import ('@/views/components/Sidentify.vue'),},
    {path:'/test',name:'test',component:()=>import ('@/views/test.vue'),},
  ]
})

export default router
