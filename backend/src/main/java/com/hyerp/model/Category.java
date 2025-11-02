package com.hyerp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 品类实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private String categoryCode; // 品类编码
    private String categoryName; // 品类名称
    private String description; // 描述
    private Integer status; // 状态 0-停用 1-启用
    private Integer sortOrder; // 排序
    private Date createTime;
    private Date updateTime;
}

