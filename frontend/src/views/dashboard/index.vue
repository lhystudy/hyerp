<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <div class="card-content">
            <div class="card-icon" style="background-color: #409EFF;">
              <i class="el-icon-goods"></i>
            </div>
            <div class="card-info">
              <div class="card-title">商品总数</div>
              <div class="card-value">{{ goodsCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="card-content">
            <div class="card-icon" style="background-color: #67C23A;">
              <i class="el-icon-s-order"></i>
            </div>
            <div class="card-info">
              <div class="card-title">订单总数</div>
              <div class="card-value">{{ orderCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="card-content">
            <div class="card-icon" style="background-color: #E6A23C;">
              <i class="el-icon-box"></i>
            </div>
            <div class="card-info">
              <div class="card-title">库存记录</div>
              <div class="card-value">{{ inventoryCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="card-content">
            <div class="card-icon" style="background-color: #F56C6C;">
              <i class="el-icon-truck"></i>
            </div>
            <div class="card-info">
              <div class="card-title">发货记录</div>
              <div class="card-value">{{ shipmentCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getGoodsList } from '@/api/goods'
import { getOrderList } from '@/api/order'
import { getInventoryList } from '@/api/inventory'
import { getShipmentList } from '@/api/shipment'

export default {
  name: 'Dashboard',
  data() {
    return {
      goodsCount: 0,
      orderCount: 0,
      inventoryCount: 0,
      shipmentCount: 0
    }
  },
  mounted() {
    this.loadDashboardData()
  },
  methods: {
    async loadDashboardData() {
      try {
        const [goodsRes, orderRes, inventoryRes, shipmentRes] = await Promise.all([
          getGoodsList(),
          getOrderList(),
          getInventoryList(),
          getShipmentList()
        ])
        this.goodsCount = goodsRes.data?.length || 0
        this.orderCount = orderRes.data?.length || 0
        this.inventoryCount = inventoryRes.data?.length || 0
        this.shipmentCount = shipmentRes.data?.length || 0
      } catch (error) {
        console.error('加载仪表盘数据失败:', error)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  .card-content {
    display: flex;
    align-items: center;
    
    .card-icon {
      width: 60px;
      height: 60px;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 15px;
      
      i {
        font-size: 30px;
        color: #fff;
      }
    }
    
    .card-info {
      flex: 1;
      
      .card-title {
        font-size: 14px;
        color: #909399;
        margin-bottom: 10px;
      }
      
      .card-value {
        font-size: 30px;
        font-weight: bold;
        color: #303133;
      }
    }
  }
}
</style>

