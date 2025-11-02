package com.hyerp.service;

import com.hyerp.dao.OrderMapper;
import com.hyerp.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    public Order getOrderById(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public Order createOrder(Order order) {
        // 生成订单号
        if (order.getOrderNo() == null || order.getOrderNo().isEmpty()) {
            order.setOrderNo("ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        } else {
            // 检查订单号是否已存在
            Order existing = orderMapper.selectByOrderNo(order.getOrderNo());
            if (existing != null) {
                throw new RuntimeException("订单号已存在");
            }
        }
        if (order.getStatus() == null) {
            order.setStatus(0); // 默认待处理
        }
        if (order.getOrderDate() == null) {
            order.setOrderDate(new Date());
        }
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderMapper.insert(order);
        return order;
    }

    public Order updateOrder(Long id, Order order) {
        Order existing = orderMapper.selectByPrimaryKey(id);
        if (existing == null) {
            throw new RuntimeException("订单不存在");
        }
        // 如果修改了订单号，检查新订单号是否已存在
        if (order.getOrderNo() != null && !existing.getOrderNo().equals(order.getOrderNo())) {
            Order orderNoExists = orderMapper.selectByOrderNo(order.getOrderNo());
            if (orderNoExists != null) {
                throw new RuntimeException("订单号已存在");
            }
        }
        order.setId(id);
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKey(order);
        return order;
    }

    public void deleteOrder(Long id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        orderMapper.deleteByPrimaryKey(id);
    }
}

