package com.hyerp.service;

import com.hyerp.dao.CategoryMapper;
import com.hyerp.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategories() {
        return categoryMapper.selectAll();
    }

    public List<Category> getEnabledCategories() {
        return categoryMapper.selectByStatus(1);
    }

    public Category getCategoryById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public Category createCategory(Category category) {
        // 检查品类编码是否已存在
        Category existing = categoryMapper.selectByCategoryCode(category.getCategoryCode());
        if (existing != null) {
            throw new RuntimeException("品类编码已存在");
        }
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        if (category.getStatus() == null) {
            category.setStatus(1); // 默认启用
        }
        if (category.getSortOrder() == null) {
            category.setSortOrder(0); // 默认排序
        }
        categoryMapper.insert(category);
        return category;
    }

    public Category updateCategory(Long id, Category category) {
        Category existing = categoryMapper.selectByPrimaryKey(id);
        if (existing == null) {
            throw new RuntimeException("品类不存在");
        }
        // 如果修改了品类编码，检查新编码是否已存在
        if (!existing.getCategoryCode().equals(category.getCategoryCode())) {
            Category codeExists = categoryMapper.selectByCategoryCode(category.getCategoryCode());
            if (codeExists != null) {
                throw new RuntimeException("品类编码已存在");
            }
        }
        category.setId(id);
        category.setUpdateTime(new Date());
        categoryMapper.updateByPrimaryKey(category);
        return category;
    }

    public void deleteCategory(Long id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new RuntimeException("品类不存在");
        }
        // 可以在这里添加检查是否有商品使用该品类的逻辑
        categoryMapper.deleteByPrimaryKey(id);
    }
}

