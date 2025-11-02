package com.hyerp.controller;

import com.hyerp.common.Result;
import com.hyerp.model.OrderItem;
import com.hyerp.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-item")
@CrossOrigin(origins = "*")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public Result<List<OrderItem>> getOrderItemList() {
        try {
            List<OrderItem> orderItemList = orderItemService.getAllOrderItems();
            return Result.success(orderItemList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<OrderItem> getOrderItem(@PathVariable Long id) {
        try {
            OrderItem orderItem = orderItemService.getOrderItemById(id);
            if (orderItem == null) {
                return Result.error(404, "订单明细不存在");
            }
            return Result.success(orderItem);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public Result<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        try {
            List<OrderItem> orderItemList = orderItemService.getOrderItemsByOrderId(orderId);
            return Result.success(orderItemList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        try {
            OrderItem created = orderItemService.createOrderItem(orderItem);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/batch")
    public Result<List<OrderItem>> createOrderItems(@RequestBody List<OrderItem> orderItems) {
        try {
            List<OrderItem> created = orderItemService.createOrderItems(orderItems);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        try {
            OrderItem updated = orderItemService.updateOrderItem(id, orderItem);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteOrderItem(@PathVariable Long id) {
        try {
            orderItemService.deleteOrderItem(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/order/{orderId}")
    public Result<Void> deleteOrderItemsByOrderId(@PathVariable Long orderId) {
        try {
            orderItemService.deleteOrderItemsByOrderId(orderId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

