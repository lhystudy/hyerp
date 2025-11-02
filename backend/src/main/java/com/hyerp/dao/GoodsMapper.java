package com.hyerp.dao;

import com.hyerp.model.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    int insert(Goods record);
    int updateByPrimaryKey(Goods record);
    int deleteByPrimaryKey(Long id);
    Goods selectByPrimaryKey(Long id);
    List<Goods> selectAll();
    List<Goods> searchByName(String name);
    Goods selectByGoodsCode(String goodsCode);
}

