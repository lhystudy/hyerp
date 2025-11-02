import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'

Vue.use(Router)

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '首页', icon: 'dashboard' }
      }
    ]
  },
  {
    path: '/goods',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Goods',
        component: () => import('@/views/goods/index'),
        meta: { title: '商品管理', icon: 'goods' }
      }
    ]
  },
  {
    path: '/order',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Order',
        component: () => import('@/views/order/index'),
        meta: { title: '订单管理', icon: 'order' }
      }
    ]
  },
  {
    path: '/inventory',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Inventory',
        component: () => import('@/views/inventory/index'),
        meta: { title: '库存管理', icon: 'inventory' }
      }
    ]
  },
  {
    path: '/shipment',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Shipment',
        component: () => import('@/views/shipment/index'),
        meta: { title: '发货管理', icon: 'shipment' }
      }
    ]
  }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export default router

