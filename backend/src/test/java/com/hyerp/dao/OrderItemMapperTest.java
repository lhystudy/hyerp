package com.hyerp.dao;

import com.hyerp.model.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
public class OrderItemMapperTest {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Test
    public void testInsert() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(1L);
        orderItem.setGoodsId(1L);
        orderItem.setQuantity(10);
        orderItem.setPrice(new BigDecimal("99.99"));
        orderItem.setSubtotal(new BigDecimal("999.90"));
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItemMapper.insert(orderItem);
        log.info("插入订单明细成功，ID: {}", orderItem.getId());
        System.out.println(orderItem);
    }

    @Test
    public void testSelectByPrimaryKey() {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(1L);
        if (orderItem != null) {
            log.info("查询订单明细成功: {}", orderItem);
            System.out.println(orderItem);
        } else {
            log.info("订单明细不存在");
        }
    }

    @Test
    public void testSelectAll() {
        List<OrderItem> orderItemList = orderItemMapper.selectAll();
        log.info("查询所有订单明细，数量: {}", orderItemList.size());
        orderItemList.forEach(System.out::println);
    }

    @Test
    public void testSelectByOrderId() {
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderId(1L);
        log.info("根据订单ID查询订单明细，数量: {}", orderItemList.size());
        orderItemList.forEach(System.out::println);
    }

    @Test
    public void testUpdateByPrimaryKey() {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(1L);
        if (orderItem != null) {
            orderItem.setQuantity(20);
            orderItem.setSubtotal(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
            orderItem.setUpdateTime(new Date());
            orderItemMapper.updateByPrimaryKey(orderItem);
            log.info("更新订单明细成功: {}", orderItem);
            System.out.println(orderItem);
        } else {
            log.info("订单明细不存在，无法更新");
        }
    }

    @Test
    public void testDeleteByPrimaryKey() {
        int result = orderItemMapper.deleteByPrimaryKey(1L);
        if (result > 0) {
            log.info("删除订单明细成功");
        } else {
            log.info("删除失败，订单明细可能不存在");
        }
    }

    @Test
    public void testDeleteByOrderId() {
        int result = orderItemMapper.deleteByOrderId(1L);
        if (result > 0) {
            log.info("删除订单的所有明细成功，删除数量: {}", result);
        } else {
            log.info("删除失败，订单可能没有明细");
        }
    }
}

