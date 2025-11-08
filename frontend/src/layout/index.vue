<template>
  <div class="app-wrapper">
    <el-container>
      <el-aside width="200px">
        <el-menu
          :default-active="activeMenu"
          router
          class="sidebar-menu"
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
        >
          <el-menu-item index="/dashboard">
            <i class="el-icon-s-home"></i>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-menu-item index="/goods/index">
            <i class="el-icon-goods"></i>
            <span slot="title">商品管理</span>
          </el-menu-item>
          <el-menu-item index="/order/index">
            <i class="el-icon-s-order"></i>
            <span slot="title">订单管理</span>
          </el-menu-item>
          <el-menu-item index="/inventory/index">
            <i class="el-icon-box"></i>
            <span slot="title">库存管理</span>
          </el-menu-item>
          <el-menu-item index="/shipment/index">
            <i class="el-icon-truck"></i>
            <span slot="title">发货管理</span>
          </el-menu-item>
          <el-menu-item index="/category/index">
            <i class="el-icon-collection-tag"></i>
            <span slot="title">品类管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="header-content">
            <h2>Hy ERP管理系统 (By Li haoyang)</h2>
            <div class="header-right">
              <el-button type="text" @click="handleLogout" icon="el-icon-switch-button">登出</el-button>
            </div>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { logout } from '@/api/auth'

export default {
  name: 'Layout',
  computed: {
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    }
  },
  methods: {
    async handleLogout() {
      this.$confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await logout()
        } catch (error) {
          // 即使后端登出失败，也清除本地token
        } finally {
          // 清除token并跳转到登录页
          this.$store.dispatch('removeToken')
          this.$router.push('/login')
          this.$message.success('已退出登录')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.app-wrapper {
  height: 100vh;
}

.el-container {
  height: 100%;
}

.el-aside {
  background-color: #304156;
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  
  .header-content {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    
    h2 {
      margin: 0;
      font-size: 20px;
      color: #303133;
    }
    
    .header-right {
      display: flex;
      align-items: center;
    }
  }
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}

.sidebar-menu {
  border-right: none;
}
</style>

