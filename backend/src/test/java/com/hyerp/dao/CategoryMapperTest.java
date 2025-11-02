package com.hyerp.dao;

import com.hyerp.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
public class CategoryMapperTest {

    @Autowired
    CategoryMapper categoryMapper;

    @Test
    public void testInsert() {
        Category category = new Category();
        category.setCategoryCode("CAT001");
        category.setCategoryName("电子产品");
        category.setDescription("各类电子产品分类");
        category.setStatus(1);
        category.setSortOrder(1);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        categoryMapper.insert(category);
        log.info("插入品类成功，ID: {}", category.getId());
        System.out.println(category);
    }

    @Test
    public void testSelectByPrimaryKey() {
        Category category = categoryMapper.selectByPrimaryKey(1L);
        if (category != null) {
            log.info("查询品类成功: {}", category);
            System.out.println(category);
        } else {
            log.info("品类不存在");
        }
    }

    @Test
    public void testSelectAll() {
        List<Category> categoryList = categoryMapper.selectAll();
        log.info("查询所有品类，数量: {}", categoryList.size());
        categoryList.forEach(System.out::println);
    }

    @Test
    public void testSelectByCategoryCode() {
        Category category = categoryMapper.selectByCategoryCode("CAT001");
        if (category != null) {
            log.info("根据品类编码查询成功: {}", category);
            System.out.println(category);
        } else {
            log.info("品类不存在");
        }
    }

    @Test
    public void testSelectByStatus() {
        List<Category> categoryList = categoryMapper.selectByStatus(1);
        log.info("查询启用状态的品类，数量: {}", categoryList.size());
        categoryList.forEach(System.out::println);
    }

    @Test
    public void testUpdateByPrimaryKey() {
        Category category = categoryMapper.selectByPrimaryKey(1L);
        if (category != null) {
            category.setCategoryName("更新后的品类名称");
            category.setDescription("更新后的描述");
            category.setSortOrder(2);
            category.setUpdateTime(new Date());
            categoryMapper.updateByPrimaryKey(category);
            log.info("更新品类成功: {}", category);
            System.out.println(category);
        } else {
            log.info("品类不存在，无法更新");
        }
    }

    @Test
    public void testDeleteByPrimaryKey() {
        int result = categoryMapper.deleteByPrimaryKey(1L);
        if (result > 0) {
            log.info("删除品类成功");
        } else {
            log.info("删除失败，品类可能不存在");
        }
    }
}

