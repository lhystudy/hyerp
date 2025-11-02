<template>
  <div class="order-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>订单管理</span>
        <el-button style="float: right; padding: 3px 0" type="primary" @click="handleAdd">新增订单</el-button>
      </div>
      
      <el-table
        v-loading="loading"
        :data="orderList"
        border
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="orderNo" label="订单号" width="150" />
        <el-table-column prop="customerName" label="客户名称" width="120" />
        <el-table-column prop="customerPhone" label="客户电话" width="120" />
        <el-table-column prop="totalAmount" label="总金额" width="120">
          <template slot-scope="scope">
            ¥{{ scope.row.totalAmount }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="orderDate" label="订单日期" width="180">
          <template slot-scope="scope">
            {{ formatDate(scope.row.orderDate) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="700px">
      <el-form ref="orderForm" :model="orderForm" :rules="rules" label-width="100px">
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="orderForm.orderNo" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="客户名称" prop="customerName">
              <el-input v-model="orderForm.customerName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户电话" prop="customerPhone">
              <el-input v-model="orderForm.customerPhone" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="客户地址" prop="customerAddress">
          <el-input v-model="orderForm.customerAddress" />
        </el-form-item>
        <el-form-item label="总金额" prop="totalAmount">
          <el-input-number v-model="orderForm.totalAmount" :precision="2" :step="0.1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="orderForm.status" style="width: 100%">
            <el-option label="待处理" :value="0" />
            <el-option label="已确认" :value="1" />
            <el-option label="已发货" :value="2" />
            <el-option label="已完成" :value="3" />
            <el-option label="已取消" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="orderForm.remark" type="textarea" :rows="3" />
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
import { getOrderList, createOrder, updateOrder, deleteOrder } from '@/api/order'

export default {
  name: 'Order',
  data() {
    return {
      loading: false,
      orderList: [],
      dialogVisible: false,
      dialogTitle: '新增订单',
      orderForm: {
        id: null,
        orderNo: '',
        customerName: '',
        customerPhone: '',
        customerAddress: '',
        totalAmount: 0,
        status: 0,
        remark: ''
      },
      rules: {
        orderNo: [{ required: true, message: '请输入订单号', trigger: 'blur' }],
        customerName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        totalAmount: [{ required: true, message: '请输入总金额', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.loadOrderList()
  },
  methods: {
    async loadOrderList() {
      this.loading = true
      try {
        const res = await getOrderList()
        this.orderList = res.data || []
      } catch (error) {
        this.$message.error('加载订单列表失败')
      } finally {
        this.loading = false
      }
    },
    handleAdd() {
      this.dialogTitle = '新增订单'
      this.orderForm = {
        id: null,
        orderNo: '',
        customerName: '',
        customerPhone: '',
        customerAddress: '',
        totalAmount: 0,
        status: 0,
        remark: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑订单'
      this.orderForm = { ...row }
      this.dialogVisible = true
    },
    async handleSubmit() {
      this.$refs.orderForm.validate(async (valid) => {
        if (valid) {
          try {
            if (this.orderForm.id) {
              await updateOrder(this.orderForm.id, this.orderForm)
              this.$message.success('更新成功')
            } else {
              await createOrder(this.orderForm)
              this.$message.success('创建成功')
            }
            this.dialogVisible = false
            this.loadOrderList()
          } catch (error) {
            this.$message.error('操作失败')
          }
        }
      })
    },
    handleDelete(row) {
      this.$confirm('确定要删除该订单吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await deleteOrder(row.id)
          this.$message.success('删除成功')
          this.loadOrderList()
        } catch (error) {
          this.$message.error('删除失败')
        }
      })
    },
    getStatusText(status) {
      const statusMap = {
        0: '待处理',
        1: '已确认',
        2: '已发货',
        3: '已完成',
        4: '已取消'
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
.order-container {
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

