package com.hyerp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemReserved {
    private Long id;
    private Long  itemId;
    private Long inventoryId;

    private Integer reservedQuantity;

    private Integer neededQuantity;
    private Date createTime;
    private Date updateTime;
}
