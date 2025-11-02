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
public class ConfigAttribute {
    private Integer id;
    private String instId;
    // 属性名称
    private String configName;
    // 属性值
    private String configValue;

    private Date createdAt;
    private Date updatedAt;
}
