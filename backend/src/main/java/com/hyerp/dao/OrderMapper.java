package com.hyerp.dao;

import com.hyerp.job.ReserveOrderJob;
import com.hyerp.model.Order;
import com.hyerp.model.dtpojo.ReservedItemPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int insert(Order record);
    int updateByPrimaryKey(Order record);
    int deleteByPrimaryKey(Long id);
    Order selectByPrimaryKey(Long id);
    List<Order> selectAll();
    Order selectByOrderNo(String orderNo);
    List<Order> selectNeedReserved();
    List<Order> selectByStatus(Integer status);

    List<ReservedItemPojo> selectOrderReservedItem(Long orderId);
}

