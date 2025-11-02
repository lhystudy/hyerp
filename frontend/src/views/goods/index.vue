<template>
  <div class="goods-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>商品管理</span>
        <el-button style="float: right; padding: 3px 0" type="primary" @click="handleAdd">新增商品</el-button>
      </div>
      
      <el-table
        v-loading="loading"
        :data="goodsList"
        border
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="goodsCode" label="商品编号" width="150" />
        <el-table-column prop="goodsName" label="商品名称" />
        <el-table-column prop="price" label="价格" width="120">
          <template slot-scope="scope">
            ¥{{ scope.row.price }}
          </template>
        </el-table-column>
        <el-table-column prop="unit" label="单位" width="100" />
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
              {{ scope.row.status === 1 ? '启用' : '停用' }}
            </el-tag>
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

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px">
      <el-form ref="goodsForm" :model="goodsForm" :rules="rules" label-width="100px">
        <el-form-item label="商品编号" prop="goodsCode">
          <el-input v-model="goodsForm.goodsCode" />
        </el-form-item>
        <el-form-item label="商品名称" prop="goodsName">
          <el-input v-model="goodsForm.goodsName" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="goodsForm.price" :precision="2" :step="0.1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="goodsForm.unit" />
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-input v-model="goodsForm.category" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="goodsForm.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="goodsForm.description" type="textarea" :rows="3" />
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
import { getGoodsList, createGoods, updateGoods, deleteGoods } from '@/api/goods'

export default {
  name: 'Goods',
  data() {
    return {
      loading: false,
      goodsList: [],
      dialogVisible: false,
      dialogTitle: '新增商品',
      goodsForm: {
        id: null,
        goodsCode: '',
        goodsName: '',
        price: 0,
        unit: '',
        category: '',
        status: 1,
        description: ''
      },
      rules: {
        goodsCode: [{ required: true, message: '请输入商品编号', trigger: 'blur' }],
        goodsName: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
        price: [{ required: true, message: '请输入价格', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.loadGoodsList()
  },
  methods: {
    async loadGoodsList() {
      this.loading = true
      try {
        const res = await getGoodsList()
        this.goodsList = res.data || []
      } catch (error) {
        this.$message.error('加载商品列表失败')
      } finally {
        this.loading = false
      }
    },
    handleAdd() {
      this.dialogTitle = '新增商品'
      this.goodsForm = {
        id: null,
        goodsCode: '',
        goodsName: '',
        price: 0,
        unit: '',
        category: '',
        status: 1,
        description: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑商品'
      this.goodsForm = { ...row }
      this.dialogVisible = true
    },
    async handleSubmit() {
      this.$refs.goodsForm.validate(async (valid) => {
        if (valid) {
          try {
            if (this.goodsForm.id) {
              await updateGoods(this.goodsForm.id, this.goodsForm)
              this.$message.success('更新成功')
            } else {
              await createGoods(this.goodsForm)
              this.$message.success('创建成功')
            }
            this.dialogVisible = false
            this.loadGoodsList()
          } catch (error) {
            this.$message.error('操作失败')
          }
        }
      })
    },
    handleDelete(row) {
      this.$confirm('确定要删除该商品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await deleteGoods(row.id)
          this.$message.success('删除成功')
          this.loadGoodsList()
        } catch (error) {
          this.$message.error('删除失败')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.goods-container {
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

