package com.hyerp.controller;

import com.hyerp.common.Result;
import com.hyerp.dto.OrderCreateDTO;
import com.hyerp.model.Order;
import com.hyerp.model.OrderItem;
import com.hyerp.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public Result<List<Order>> getOrderList() {
        try {
            List<Order> orderList = orderService.getAllOrders();
            return Result.success(orderList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Order> getOrder(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            if (order == null) {
                return Result.error(404, "订单不存在");
            }
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result<Order> createOrder(@RequestBody OrderCreateDTO orderDTO) {
        try {
            Order order = new Order();
            BeanUtils.copyProperties(orderDTO, order);
            List<OrderItem> orderItems = orderDTO.getOrderItems();
            Order created = orderService.createOrder(order, orderItems);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        try {
            Order updated = orderService.updateOrder(id, order);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

