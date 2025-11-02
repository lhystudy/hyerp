package com.hyerp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long id;
    private Long orderId; // 订单ID
    private Long goodsId; // 商品ID
    private Integer quantity; // 商品数量
    private BigDecimal price; // 单价（下单时的商品价格）
    private BigDecimal subtotal; // 小计（price * quantity）
    private Date createTime;
    private Date updateTime;
}

