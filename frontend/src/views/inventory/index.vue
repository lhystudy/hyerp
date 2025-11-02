<template>
  <div class="inventory-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>库存管理</span>
        <el-button style="float: right; padding: 3px 0" type="primary" @click="handleAdd">新增库存</el-button>
      </div>
      
      <el-table
        v-loading="loading"
        :data="inventoryList"
        border
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="goodsName" label="商品" width="200" />
        <el-table-column prop="quantity" label="库存数量" width="120" />
        <el-table-column prop="warehouseName" label="仓库名称" width="150" />
        <el-table-column prop="warehouseLocation" label="仓库位置" width="200" />
        <el-table-column prop="minStock" label="最小库存" width="120" />
        <el-table-column prop="maxStock" label="最大库存" width="120" />
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px">
      <el-form ref="inventoryForm" :model="inventoryForm" :rules="rules" label-width="100px">
        <el-form-item label="商品" prop="goodsId">
          <el-select v-model="inventoryForm.goodsId" placeholder="请选择商品" style="width: 100%" clearable filterable>
            <el-option
              v-for="item in goodsOptions"
              :key="item.id"
              :label="`${item.goodsCode} - ${item.goodsName}`"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="库存数量" prop="quantity">
          <el-input-number v-model="inventoryForm.quantity" style="width: 100%" />
        </el-form-item>
        <el-form-item label="仓库名称" prop="warehouseName">
          <el-input v-model="inventoryForm.warehouseName" />
        </el-form-item>
        <el-form-item label="仓库位置" prop="warehouseLocation">
          <el-input v-model="inventoryForm.warehouseLocation" />
        </el-form-item>
        <el-form-item label="最小库存" prop="minStock">
          <el-input-number v-model="inventoryForm.minStock" style="width: 100%" />
        </el-form-item>
        <el-form-item label="最大库存" prop="maxStock">
          <el-input-number v-model="inventoryForm.maxStock" style="width: 100%" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="inventoryForm.remark" type="textarea" :rows="3" />
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
import { getInventoryList, createInventory, updateInventory, deleteInventory } from '@/api/inventory'
import { getGoodsList } from '@/api/goods'

export default {
  name: 'Inventory',
  data() {
    return {
      loading: false,
      inventoryList: [],
      goodsOptions: [],
      dialogVisible: false,
      dialogTitle: '新增库存',
      inventoryForm: {
        id: null,
        goodsId: null,
        quantity: 0,
        warehouseName: '',
        warehouseLocation: '',
        minStock: 0,
        maxStock: 0,
        remark: ''
      },
      rules: {
        goodsId: [{ required: true, message: '请选择商品', trigger: 'change' }],
        quantity: [{ required: true, message: '请输入库存数量', trigger: 'blur' }]
      }
    }
  },
  async mounted() {
    await this.loadGoodsOptions()
    await this.loadInventoryList()
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
    async loadInventoryList() {
      this.loading = true
      try {
        const res = await getInventoryList()
        const inventoryList = res.data || []
        // 处理库存列表，添加商品名称显示
        this.inventoryList = inventoryList.map(inventory => {
          const goods = this.goodsOptions.find(g => g.id === inventory.goodsId)
          return {
            ...inventory,
            goodsName: goods ? `${goods.goodsCode} - ${goods.goodsName}` : `商品ID: ${inventory.goodsId}`
          }
        })
      } catch (error) {
        this.$message.error('加载库存列表失败')
      } finally {
        this.loading = false
      }
    },
    async handleAdd() {
      this.dialogTitle = '新增库存'
      // 确保商品选项已加载
      if (this.goodsOptions.length === 0) {
        await this.loadGoodsOptions()
      }
      this.inventoryForm = {
        id: null,
        goodsId: null,
        quantity: 0,
        warehouseName: '',
        warehouseLocation: '',
        minStock: 0,
        maxStock: 0,
        remark: ''
      }
      this.dialogVisible = true
    },
    async handleEdit(row) {
      this.dialogTitle = '编辑库存'
      // 确保商品选项已加载
      if (this.goodsOptions.length === 0) {
        await this.loadGoodsOptions()
      }
      this.inventoryForm = { ...row }
      this.dialogVisible = true
    },
    async handleSubmit() {
      this.$refs.inventoryForm.validate(async (valid) => {
        if (valid) {
          try {
            if (this.inventoryForm.id) {
              await updateInventory(this.inventoryForm.id, this.inventoryForm)
              this.$message.success('更新成功')
            } else {
              await createInventory(this.inventoryForm)
              this.$message.success('创建成功')
            }
            this.dialogVisible = false
            await this.loadGoodsOptions()
            await this.loadInventoryList()
          } catch (error) {
            this.$message.error('操作失败')
          }
        }
      })
    },
    handleDelete(row) {
      this.$confirm('确定要删除该库存记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await deleteInventory(row.id)
          this.$message.success('删除成功')
          this.loadInventoryList()
        } catch (error) {
          this.$message.error('删除失败')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.inventory-container {
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

