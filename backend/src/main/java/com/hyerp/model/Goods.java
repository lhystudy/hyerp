package com.hyerp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private Long id;
    private String goodsCode;
    private String goodsName;
    private String description;
    private BigDecimal price;
    private String unit;
    private String category;
    private Integer status; // 0-停用 1-启用
    private Date createTime;
    private Date updateTime;
}

