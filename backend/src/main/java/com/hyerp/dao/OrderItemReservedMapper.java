package com.hyerp.dao;

import com.hyerp.model.OrderItemReserved;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemReservedMapper {
    int insert(OrderItemReserved record);
    int insertBatch(List<OrderItemReserved> records);
    int updateByPrimaryKey(OrderItemReserved record);
    int deleteByPrimaryKey(Long id);
    OrderItemReserved selectByPrimaryKey(Long id);
    List<OrderItemReserved> selectAll();
}

