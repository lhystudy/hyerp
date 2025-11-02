<template>
  <div class="category-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>品类管理</span>
        <el-button style="float: right; padding: 3px 0" type="primary" @click="handleAdd">新增品类</el-button>
      </div>
      
      <el-table
        v-loading="loading"
        :data="categoryList"
        border
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="categoryCode" label="品类编码" width="150" />
        <el-table-column prop="categoryName" label="品类名称" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="sortOrder" label="排序" width="100" />
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
      <el-form ref="categoryForm" :model="categoryForm" :rules="rules" label-width="100px">
        <el-form-item label="品类编码" prop="categoryCode">
          <el-input v-model="categoryForm.categoryCode" />
        </el-form-item>
        <el-form-item label="品类名称" prop="categoryName">
          <el-input v-model="categoryForm.categoryName" />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="categoryForm.sortOrder" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="categoryForm.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="categoryForm.description" type="textarea" :rows="3" />
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
import { getCategoryList, createCategory, updateCategory, deleteCategory } from '@/api/category'

export default {
  name: 'Category',
  data() {
    return {
      loading: false,
      categoryList: [],
      dialogVisible: false,
      dialogTitle: '新增品类',
      categoryForm: {
        id: null,
        categoryCode: '',
        categoryName: '',
        description: '',
        status: 1,
        sortOrder: 0
      },
      rules: {
        categoryCode: [{ required: true, message: '请输入品类编码', trigger: 'blur' }],
        categoryName: [{ required: true, message: '请输入品类名称', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.loadCategoryList()
  },
  methods: {
    async loadCategoryList() {
      this.loading = true
      try {
        const res = await getCategoryList()
        this.categoryList = res.data || []
      } catch (error) {
        this.$message.error('加载品类列表失败')
      } finally {
        this.loading = false
      }
    },
    handleAdd() {
      this.dialogTitle = '新增品类'
      this.categoryForm = {
        id: null,
        categoryCode: '',
        categoryName: '',
        description: '',
        status: 1,
        sortOrder: 0
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑品类'
      this.categoryForm = { ...row }
      this.dialogVisible = true
    },
    async handleSubmit() {
      this.$refs.categoryForm.validate(async (valid) => {
        if (valid) {
          try {
            if (this.categoryForm.id) {
              await updateCategory(this.categoryForm.id, this.categoryForm)
              this.$message.success('更新成功')
            } else {
              await createCategory(this.categoryForm)
              this.$message.success('创建成功')
            }
            this.dialogVisible = false
            this.loadCategoryList()
          } catch (error) {
            this.$message.error(error.message || '操作失败')
          }
        }
      })
    },
    handleDelete(row) {
      this.$confirm('确定要删除该品类吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await deleteCategory(row.id)
          this.$message.success('删除成功')
          this.loadCategoryList()
        } catch (error) {
          this.$message.error(error.message || '删除失败')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.category-container {
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

