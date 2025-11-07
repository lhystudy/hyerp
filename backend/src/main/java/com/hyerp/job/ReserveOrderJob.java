package com.hyerp.job;

import com.hyerp.dao.InventoryMapper;
import com.hyerp.dao.OrderItemReservedMapper;
import com.hyerp.dao.OrderMapper;
import com.hyerp.model.Inventory;
import com.hyerp.model.Order;
import com.hyerp.model.OrderItemReserved;
import com.hyerp.model.dtpojo.ReservedItemPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
@EnableScheduling
public class ReserveOrderJob {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemReservedMapper orderItemReservedMapper;

    @Autowired
    InventoryMapper inventoryMapper;

    @Scheduled(fixedRate = 3000)
    public void executeReserveOrder() {

        List<Order> orderList = orderMapper.selectNeedReserved();
        for (Order order : orderList) {
            // 执行预定动作
            reserveOrder(order.getId());
            // 预定完以后扫描并更新订单状态
            updateOrderReserved(order.getId());
        }
    }

    /**
     * 预定完以后更新订单状态
     * @param orderId
     * @return
     */
    @Transactional
    public int updateOrderReserved(Long orderId) {
        List<ReservedItemPojo> reservedItemPojoList = orderMapper.selectOrderReservedItem(orderId);
        boolean allIsReserved = true;
        int res = 0;
        for (ReservedItemPojo reservedItemPojo: reservedItemPojoList) {
            // 如果不是所有的商品项都完成预定
            if (reservedItemPojo.getOrderQuantity() != reservedItemPojo.getReservedQuantity()) {
                allIsReserved = false;
            }
        }
        // 如果所有商品都完成了预定，则将订单更新为已预定-发货状态
        if (allIsReserved) {
            Order order = orderMapper.selectByPrimaryKey(orderId);
            order.setStatus(2);
            order.setUpdateTime(new Date());
            res = orderMapper.updateByPrimaryKey(order);
        }
        return res;
    }

    /**
     * 按订单完成预定
     * @param orderId
     * @return
     */
    @Transactional
    public int reserveOrder(Long orderId) {
        List<ReservedItemPojo> reservedItemPojoList = orderMapper.selectOrderReservedItem(orderId);
        for (ReservedItemPojo reservedItemPojo: reservedItemPojoList) {
            int nowNeedReservedCount;
            OrderItemReserved reserved;
            // 还未创建预定项
            if (reservedItemPojo.getReservedId() == null) {
                reserved = new OrderItemReserved();
                reserved.setItemId(reservedItemPojo.getItemId());
                reserved.setInventoryId(0L);
                reserved.setNeededQuantity(reservedItemPojo.getOrderQuantity());
                reserved.setReservedQuantity(0);
                reserved.setCreateTime(new Date());
                reserved.setUpdateTime(new Date());
                orderItemReservedMapper.insert(reserved);
                nowNeedReservedCount = reserved.getNeededQuantity();
            } else {
                reserved = orderItemReservedMapper.selectByPrimaryKey(reservedItemPojo.getReservedId());
                // 计算获取当前任然需要预定的数量
                nowNeedReservedCount = reservedItemPojo.getNeededQuantity() - reservedItemPojo.getReservedQuantity();
            }
            // 如果预定已满足则跳过
            if (nowNeedReservedCount == 0) {
                continue;
            }
            Inventory inventory = inventoryMapper.selectByGoodsId(reservedItemPojo.getGoodsId());
            int nowInventoryQuantity = inventory.getQuantity() - nowNeedReservedCount;

            // 如果当前库存可以执行预定
            if (nowInventoryQuantity >= 0) {
                // 库存减少当前需预定数量
                inventory.setQuantity(nowInventoryQuantity);
                inventory.setUpdateTime(new Date());
                // 预定项更新库存信息
                reserved.setReservedQuantity(reservedItemPojo.getOrderQuantity());
//                reserved.setNeededQuantity(reservedItemPojo.getOrderQuantity() - nowNeedReservedCount);
                reserved.setInventoryId(inventory.getId());
                reserved.setUpdateTime(new Date());

                inventoryMapper.updateByPrimaryKey(inventory);
                orderItemReservedMapper.updateByPrimaryKey(reserved);
            }
        }
        return reservedItemPojoList.size();
    }
}
