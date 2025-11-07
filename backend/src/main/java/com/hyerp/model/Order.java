package com.hyerp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private String orderNo;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private BigDecimal totalAmount;
    private Integer status; // 0-待处理 1-已确认 2-已预定 3-已发货 4-已完成 5-已取消
    private Date orderDate;
    private Date deliveryDate;
    private String remark;
    private Date createTime;
    private Date updateTime;
}

