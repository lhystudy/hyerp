package com.hyerp.dao;

import com.hyerp.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
public class OrderMapperTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void testInsert() {
        Order order = new Order();
        order.setOrderNo("ORD" + System.currentTimeMillis());
        order.setCustomerName("张三");
        order.setCustomerPhone("13800138000");
        order.setCustomerAddress("北京市朝阳区测试街道123号");
        order.setTotalAmount(new BigDecimal("299.99"));
        order.setStatus(0);
        order.setOrderDate(new Date());
        order.setRemark("测试订单");
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderMapper.insert(order);
        log.info("插入订单成功，ID: {}", order.getId());
        System.out.println(order);
    }

    @Test
    public void testSelectByPrimaryKey() {
        Order order = orderMapper.selectByPrimaryKey(1L);
        if (order != null) {
            log.info("查询订单成功: {}", order);
            System.out.println(order);
        } else {
            log.info("订单不存在");
        }
    }

    @Test
    public void testSelectAll() {
        List<Order> orderList = orderMapper.selectAll();
        log.info("查询所有订单，数量: {}", orderList.size());
        orderList.forEach(System.out::println);
    }

    @Test
    public void testSelectByOrderNo() {
        Order order = orderMapper.selectByOrderNo("ORD001");
        if (order != null) {
            log.info("根据订单号查询成功: {}", order);
            System.out.println(order);
        } else {
            log.info("订单不存在");
        }
    }

    @Test
    public void testUpdateByPrimaryKey() {
        Order order = orderMapper.selectByPrimaryKey(1L);
        if (order != null) {
            order.setCustomerName("李四");
            order.setStatus(1);
            order.setTotalAmount(new BigDecimal("399.99"));
            order.setUpdateTime(new Date());
            orderMapper.updateByPrimaryKey(order);
            log.info("更新订单成功: {}", order);
            System.out.println(order);
        } else {
            log.info("订单不存在，无法更新");
        }
    }

    @Test
    public void testDeleteByPrimaryKey() {
        int result = orderMapper.deleteByPrimaryKey(1L);
        if (result > 0) {
            log.info("删除订单成功");
        } else {
            log.info("删除失败，订单可能不存在");
        }
    }
}

