package com.hyerp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 库存实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private Long id;
    private Long goodsId;
    private Integer quantity;
    private String warehouseName;
    private String warehouseLocation;
    private Integer minStock;
    private Integer maxStock;
    private String remark;
    private Date createTime;
    private Date updateTime;
}

