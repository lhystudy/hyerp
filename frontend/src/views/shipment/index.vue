<template>
  <div class="shipment-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>发货管理</span>
        <el-button style="float: right; padding: 3px 0" type="primary" @click="handleAdd">新增发货</el-button>
      </div>
      
      <el-table
        v-loading="loading"
        :data="shipmentList"
        border
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="orderId" label="订单ID" width="100" />
        <el-table-column prop="shipmentNo" label="发货单号" width="150" />
        <el-table-column prop="receiverName" label="收货人" width="120" />
        <el-table-column prop="receiverPhone" label="收货电话" width="120" />
        <el-table-column prop="status" label="状态" width="120">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="shipDate" label="发货日期" width="180">
          <template slot-scope="scope">
            {{ formatDate(scope.row.shipDate) }}
          </template>
        </el-table-column>
        <el-table-column prop="trackingNumber" label="物流单号" width="150" />
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="800px">
      <el-form ref="shipmentForm" :model="shipmentForm" :rules="rules" label-width="120px">
        <el-form-item label="订单ID" prop="orderId">
          <el-input-number v-model="shipmentForm.orderId" style="width: 100%" />
        </el-form-item>
        <el-form-item label="发货单号" prop="shipmentNo">
          <el-input v-model="shipmentForm.shipmentNo" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="发货人姓名" prop="shipperName">
              <el-input v-model="shipmentForm.shipperName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发货人电话" prop="shipperPhone">
              <el-input v-model="shipmentForm.shipperPhone" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="发货地址" prop="shippingAddress">
          <el-input v-model="shipmentForm.shippingAddress" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="收货人姓名" prop="receiverName">
              <el-input v-model="shipmentForm.receiverName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收货人电话" prop="receiverPhone">
              <el-input v-model="shipmentForm.receiverPhone" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="收货地址" prop="receiverAddress">
          <el-input v-model="shipmentForm.receiverAddress" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="shipmentForm.status" style="width: 100%">
            <el-option label="待发货" :value="0" />
            <el-option label="已发货" :value="1" />
            <el-option label="运输中" :value="2" />
            <el-option label="已送达" :value="3" />
            <el-option label="异常" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="物流单号" prop="trackingNumber">
          <el-input v-model="shipmentForm.trackingNumber" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="shipmentForm.remark" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getShipmentList, createShipment, updateShipment, deleteShipment } from '@/api/shipment'

export default {
  name: 'Shipment',
  data() {
    return {
      loading: false,
      shipmentList: [],
      dialogVisible: false,
      dialogTitle: '新增发货',
      shipmentForm: {
        id: null,
        orderId: null,
        shipmentNo: '',
        shipperName: '',
        shipperPhone: '',
        shippingAddress: '',
        receiverName: '',
        receiverPhone: '',
        receiverAddress: '',
        status: 0,
        trackingNumber: '',
        remark: ''
      },
      rules: {
        orderId: [{ required: true, message: '请输入订单ID', trigger: 'blur' }],
        shipmentNo: [{ required: true, message: '请输入发货单号', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.loadShipmentList()
  },
  methods: {
    async loadShipmentList() {
      this.loading = true
      try {
        const res = await getShipmentList()
        this.shipmentList = res.data || []
      } catch (error) {
        this.$message.error('加载发货列表失败')
      } finally {
        this.loading = false
      }
    },
    handleAdd() {
      this.dialogTitle = '新增发货'
      this.shipmentForm = {
        id: null,
        orderId: null,
        shipmentNo: '',
        shipperName: '',
        shipperPhone: '',
        shippingAddress: '',
        receiverName: '',
        receiverPhone: '',
        receiverAddress: '',
        status: 0,
        trackingNumber: '',
        remark: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑发货'
      this.shipmentForm = { ...row }
      this.dialogVisible = true
    },
    async handleSubmit() {
      this.$refs.shipmentForm.validate(async (valid) => {
        if (valid) {
          try {
            if (this.shipmentForm.id) {
              await updateShipment(this.shipmentForm.id, this.shipmentForm)
              this.$message.success('更新成功')
            } else {
              await createShipment(this.shipmentForm)
              this.$message.success('创建成功')
            }
            this.dialogVisible = false
            this.loadShipmentList()
          } catch (error) {
            this.$message.error('操作失败')
          }
        }
      })
    },
    handleDelete(row) {
      this.$confirm('确定要删除该发货记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await deleteShipment(row.id)
          this.$message.success('删除成功')
          this.loadShipmentList()
        } catch (error) {
          this.$message.error('删除失败')
        }
      })
    },
    getStatusText(status) {
      const statusMap = {
        0: '待发货',
        1: '已发货',
        2: '运输中',
        3: '已送达',
        4: '异常'
      }
      return statusMap[status] || '未知'
    },
    getStatusType(status) {
      const typeMap = {
        0: 'warning',
        1: 'info',
        2: 'primary',
        3: 'success',
        4: 'danger'
      }
      return typeMap[status] || 'info'
    },
    formatDate(date) {
      if (!date) return ''
      return new Date(date).toLocaleString('zh-CN')
    }
  }
}
</script>

<style lang="scss" scoped>
.shipment-container {
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both;
  }
}
</style>

