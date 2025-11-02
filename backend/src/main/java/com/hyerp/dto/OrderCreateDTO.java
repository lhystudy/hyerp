package com.hyerp.dto;

import com.hyerp.model.OrderItem;
import lombok.Data;
import java.util.List;

/**
 * 订单创建DTO
 */
@Data
public class OrderCreateDTO {
    private Long id;
    private String orderNo;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private java.math.BigDecimal totalAmount;
    private Integer status;
    private java.util.Date orderDate;
    private java.util.Date deliveryDate;
    private String remark;
    private List<OrderItem> orderItems; // 订单明细列表
}

