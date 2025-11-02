package com.hyerp.dao;

import com.hyerp.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int insert(Category record);
    int updateByPrimaryKey(Category record);
    int deleteByPrimaryKey(Long id);
    Category selectByPrimaryKey(Long id);
    List<Category> selectAll();
    Category selectByCategoryCode(String categoryCode);
    List<Category> selectByStatus(Integer status);
}

