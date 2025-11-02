package com.hyerp.service;

import com.hyerp.dao.GoodsMapper;
import com.hyerp.dao.OrderItemMapper;
import com.hyerp.dao.OrderMapper;
import com.hyerp.model.Goods;
import com.hyerp.model.Order;
import com.hyerp.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    public Order getOrderById(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public Order createOrder(Order order, List<OrderItem> orderItems) {
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
        
        // 如果有订单明细，计算总金额
        if (orderItems != null && !orderItems.isEmpty()) {
            BigDecimal totalAmount = BigDecimal.ZERO;
            Date now = new Date();
            
            for (OrderItem item : orderItems) {
                // 如果没有价格，从商品获取
                if (item.getPrice() == null && item.getGoodsId() != null) {
                    Goods goods = goodsMapper.selectByPrimaryKey(item.getGoodsId());
                    if (goods != null && goods.getPrice() != null) {
                        item.setPrice(goods.getPrice());
                    }
                }
                
                if (item.getPrice() != null && item.getQuantity() != null) {
                    item.setSubtotal(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
                    totalAmount = totalAmount.add(item.getSubtotal());
                    item.setOrderId(null); // 会在插入订单后设置
                    item.setCreateTime(now);
                    item.setUpdateTime(now);
                }
            }
            order.setTotalAmount(totalAmount);
        }
        
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderMapper.insert(order);
        
        // 创建订单明细
        if (orderItems != null && !orderItems.isEmpty()) {
            for (OrderItem item : orderItems) {
                item.setOrderId(order.getId());
            }
            orderItemMapper.insertBatch(orderItems);
        }
        
        return order;
    }

    public Order createOrder(Order order) {
        return createOrder(order, null);
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

