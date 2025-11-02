package com.hyerp.dao;

import com.hyerp.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    int insert(OrderItem record);
    int insertBatch(List<OrderItem> records);
    int updateByPrimaryKey(OrderItem record);
    int deleteByPrimaryKey(Long id);
    int deleteByOrderId(Long orderId);
    OrderItem selectByPrimaryKey(Long id);
    List<OrderItem> selectAll();
    List<OrderItem> selectByOrderId(Long orderId);
}

