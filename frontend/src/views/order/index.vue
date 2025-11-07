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
        <el-table-column label="操作" width="280" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="success" @click="handleConfirm(scope.row)" 
                       :disabled="scope.row.status !== 0">确认</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="900px">
      <el-form ref="orderForm" :model="orderForm" :rules="rules" label-width="100px">
        <el-form-item label="订单号" prop="orderNo" v-if="orderForm.id">
          <el-input v-model="orderForm.orderNo" :disabled="true" />
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
        
        <!-- 订单明细 -->
        <el-form-item label="订单明细" prop="orderItems" v-if="!orderForm.id">
          <div style="width: 100%; border: 1px solid #DCDFE6; border-radius: 4px; padding: 10px;">
            <div style="margin-bottom: 10px;">
              <el-button size="small" type="primary" icon="el-icon-plus" @click="addOrderItem">添加商品</el-button>
            </div>
            <el-table :data="orderForm.orderItems" border size="small" style="width: 100%">
              <el-table-column label="商品" width="250">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.goodsId" placeholder="请选择商品" style="width: 100%" 
                             @change="handleGoodsChange(scope.$index)" filterable>
                    <el-option
                      v-for="item in goodsOptions"
                      :key="item.id"
                      :label="`${item.goodsCode} - ${item.goodsName} (¥${item.price})`"
                      :value="item.id"
                    />
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="单价" width="120">
                <template slot-scope="scope">
                  <span>¥{{ scope.row.price || '0.00' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="数量" width="120">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.quantity" :min="1" :precision="0" 
                                   style="width: 100%" @change="calculateTotal" />
                </template>
              </el-table-column>
              <el-table-column label="小计" width="120">
                <template slot-scope="scope">
                  <span>¥{{ (scope.row.price * scope.row.quantity || 0).toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="80">
                <template slot-scope="scope">
                  <el-button size="mini" type="danger" icon="el-icon-delete" 
                             @click="removeOrderItem(scope.$index)"></el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px; text-align: right; padding-right: 10px;">
              <strong>总金额：¥{{ orderForm.totalAmount.toFixed(2) }}</strong>
            </div>
          </div>
        </el-form-item>
        
        <!-- 编辑模式显示总金额 -->
        <el-form-item label="总金额" prop="totalAmount" v-if="orderForm.id">
          <el-input-number v-model="orderForm.totalAmount" :precision="2" :step="0.1" style="width: 100%" />
        </el-form-item>
        
        <el-form-item label="状态" prop="status">
          <el-select 
            v-model="orderForm.status" 
            :disabled="orderForm.id ? false : true"
            style="width: 100%">
            <el-option label="待处理" :value="0" />
            <el-option label="已确认" :value="1" />
            <el-option label="已预定" :value="2" />
            <el-option label="已发货" :value="3" />
            <el-option label="已完成" :value="4" />
            <el-option label="已取消" :value="5" />
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
import { getOrderList, createOrder, updateOrder, deleteOrder, confirmOrder } from '@/api/order'
import { getGoodsList } from '@/api/goods'

export default {
  name: 'Order',
  data() {
    return {
      loading: false,
      orderList: [],
      goodsOptions: [],
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
        remark: '',
        orderItems: []
      },
      rules: {
        customerName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        orderItems: [
          { 
            validator: (rule, value, callback) => {
              if (!this.orderForm.id && (!value || value.length === 0)) {
                callback(new Error('请至少添加一个商品'))
              } else {
                callback()
              }
            }, 
            trigger: 'change' 
          }
        ]
      }
    }
  },
  async mounted() {
    await this.loadGoodsOptions()
    await this.loadOrderList()
  },
  methods: {
    async loadGoodsOptions() {
      try {
        const res = await getGoodsList()
        this.goodsOptions = res.data || []
      } catch (error) {
        this.$message.error('加载商品列表失败')
      }
    },
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
    addOrderItem() {
      this.orderForm.orderItems.push({
        goodsId: null,
        quantity: 1,
        price: 0
      })
    },
    removeOrderItem(index) {
      this.orderForm.orderItems.splice(index, 1)
      this.calculateTotal()
    },
    handleGoodsChange(index) {
      const item = this.orderForm.orderItems[index]
      if (item.goodsId) {
        const goods = this.goodsOptions.find(g => g.id === item.goodsId)
        if (goods) {
          item.price = parseFloat(goods.price)
          this.calculateTotal()
        }
      }
    },
    calculateTotal() {
      let total = 0
      this.orderForm.orderItems.forEach(item => {
        if (item.price && item.quantity) {
          total += item.price * item.quantity
        }
      })
      this.orderForm.totalAmount = total
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
        remark: '',
        orderItems: []
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
              // 编辑订单
              const { orderItems, ...orderData } = this.orderForm
              await updateOrder(this.orderForm.id, orderData)
              this.$message.success('更新成功')
            } else {
              // 新增订单，需要包含订单明细
              const submitData = {
                ...this.orderForm,
                orderItems: this.orderForm.orderItems.map(item => ({
                  goodsId: item.goodsId,
                  quantity: item.quantity,
                  price: item.price
                }))
              }
              await createOrder(submitData)
              this.$message.success('创建成功')
            }
            this.dialogVisible = false
            await this.loadOrderList()
          } catch (error) {
            this.$message.error(error.message || '操作失败')
          }
        }
      })
    },
    async handleConfirm(row) {
      this.$confirm('确定要确认该订单吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await confirmOrder(row.id)
          this.$message.success('订单确认成功')
          this.loadOrderList()
        } catch (error) {
          this.$message.error('订单确认失败')
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
        2: '已预定',
        3: '已发货',
        4: '已完成',
        5: '已取消'
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

