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

@Service
public class OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    public List<OrderItem> getAllOrderItems() {
        return orderItemMapper.selectAll();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemMapper.selectByPrimaryKey(id);
    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemMapper.selectByOrderId(orderId);
    }

    @Transactional
    public OrderItem createOrderItem(OrderItem orderItem) {
        // 验证订单是否存在
        Order order = orderMapper.selectByPrimaryKey(orderItem.getOrderId());
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        // 验证商品是否存在
        Goods goods = goodsMapper.selectByPrimaryKey(orderItem.getGoodsId());
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }

        // 如果未提供价格，使用商品当前价格
        if (orderItem.getPrice() == null) {
            orderItem.setPrice(goods.getPrice());
        }

        // 计算小计
        if (orderItem.getSubtotal() == null) {
            orderItem.setSubtotal(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
        }

        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItemMapper.insert(orderItem);
        return orderItem;
    }

    @Transactional
    public List<OrderItem> createOrderItems(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new RuntimeException("订单明细不能为空");
        }

        Date now = new Date();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItem item : orderItems) {
            // 验证订单是否存在
            Order order = orderMapper.selectByPrimaryKey(item.getOrderId());
            if (order == null) {
                throw new RuntimeException("订单不存在: " + item.getOrderId());
            }

            // 验证商品是否存在
            Goods goods = goodsMapper.selectByPrimaryKey(item.getGoodsId());
            if (goods == null) {
                throw new RuntimeException("商品不存在: " + item.getGoodsId());
            }

            // 如果未提供价格，使用商品当前价格
            if (item.getPrice() == null) {
                item.setPrice(goods.getPrice());
            }

            // 计算小计
            if (item.getSubtotal() == null) {
                item.setSubtotal(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
            }

            totalAmount = totalAmount.add(item.getSubtotal());
            item.setCreateTime(now);
            item.setUpdateTime(now);
        }

        // 批量插入
        orderItemMapper.insertBatch(orderItems);

        // 更新订单总金额
        Order order = orderMapper.selectByPrimaryKey(orderItems.get(0).getOrderId());
        order.setTotalAmount(totalAmount);
        order.setUpdateTime(now);
        orderMapper.updateByPrimaryKey(order);

        return orderItems;
    }

    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        OrderItem existing = orderItemMapper.selectByPrimaryKey(id);
        if (existing == null) {
            throw new RuntimeException("订单明细不存在");
        }

        // 验证商品是否存在
        if (orderItem.getGoodsId() != null) {
            Goods goods = goodsMapper.selectByPrimaryKey(orderItem.getGoodsId());
            if (goods == null) {
                throw new RuntimeException("商品不存在");
            }

            // 如果修改了商品，更新价格
            if (!existing.getGoodsId().equals(orderItem.getGoodsId())) {
                orderItem.setPrice(goods.getPrice());
            }
        }

        // 重新计算小计
        if (orderItem.getPrice() != null && orderItem.getQuantity() != null) {
            orderItem.setSubtotal(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
        }

        orderItem.setId(id);
        orderItem.setUpdateTime(new Date());
        orderItemMapper.updateByPrimaryKey(orderItem);
        return orderItem;
    }

    @Transactional
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        if (orderItem == null) {
            throw new RuntimeException("订单明细不存在");
        }
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void deleteOrderItemsByOrderId(Long orderId) {
        orderItemMapper.deleteByOrderId(orderId);
    }
}

