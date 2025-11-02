package com.hyerp.dao;

import com.hyerp.model.Shipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShipmentMapper {
    int insert(Shipment record);
    int updateByPrimaryKey(Shipment record);
    int deleteByPrimaryKey(Long id);
    Shipment selectByPrimaryKey(Long id);
    List<Shipment> selectAll();
    List<Shipment> selectByOrderId(Long orderId);
}

