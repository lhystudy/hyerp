package com.hyerp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 发货实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {
    private Long id;
    private Long orderId;
    private String shipmentNo;
    private String shipperName;
    private String shipperPhone;
    private String shippingAddress;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private Integer status; // 0-待发货 1-已发货 2-运输中 3-已送达 4-异常
    private Date shipDate;
    private Date expectedDeliveryDate;
    private Date actualDeliveryDate;
    private String trackingNumber;
    private String remark;
    private Date createTime;
    private Date updateTime;
}

