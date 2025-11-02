package com.hyerp.dao;

import com.hyerp.model.Inventory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryMapper {
    int insert(Inventory record);
    int updateByPrimaryKey(Inventory record);
    int deleteByPrimaryKey(Long id);
    Inventory selectByPrimaryKey(Long id);
    List<Inventory> selectAll();
    List<Inventory> selectByGoodsId(Long goodsId);
}

