package com.hyerp.model.dtpojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservedItemPojo {
    private Long orderId; // 商品ID
    private Long itemId;
    private Long goodsId;
    private Integer orderQuantity; // 商品数量
    private Long reservedId;

    private Long inventoryId;
    private Integer reservedQuantity;
    private Integer neededQuantity;

}
