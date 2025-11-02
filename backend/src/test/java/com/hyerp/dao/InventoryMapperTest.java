package com.hyerp.dao;

import com.hyerp.model.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
public class InventoryMapperTest {

    @Autowired
    InventoryMapper inventoryMapper;

    @Test
    public void testInsert() {
        Inventory inventory = new Inventory();
        inventory.setGoodsId(1L);
        inventory.setQuantity(100);
        inventory.setWarehouseName("北京仓库");
        inventory.setWarehouseLocation("北京市朝阳区仓库路1号");
        inventory.setMinStock(10);
        inventory.setMaxStock(1000);
        inventory.setRemark("测试库存");
        inventory.setCreateTime(new Date());
        inventory.setUpdateTime(new Date());
        inventoryMapper.insert(inventory);
        log.info("插入库存成功，ID: {}", inventory.getId());
        System.out.println(inventory);
    }

    @Test
    public void testSelectByPrimaryKey() {
        Inventory inventory = inventoryMapper.selectByPrimaryKey(1L);
        if (inventory != null) {
            log.info("查询库存成功: {}", inventory);
            System.out.println(inventory);
        } else {
            log.info("库存记录不存在");
        }
    }

    @Test
    public void testSelectAll() {
        List<Inventory> inventoryList = inventoryMapper.selectAll();
        log.info("查询所有库存，数量: {}", inventoryList.size());
        inventoryList.forEach(System.out::println);
    }

    @Test
    public void testSelectByGoodsId() {
        List<Inventory> inventoryList = inventoryMapper.selectByGoodsId(1L);
        log.info("根据商品ID查询库存，数量: {}", inventoryList.size());
        inventoryList.forEach(System.out::println);
    }

    @Test
    public void testUpdateByPrimaryKey() {
        Inventory inventory = inventoryMapper.selectByPrimaryKey(1L);
        if (inventory != null) {
            inventory.setQuantity(200);
            inventory.setWarehouseName("上海仓库");
            inventory.setUpdateTime(new Date());
            inventoryMapper.updateByPrimaryKey(inventory);
            log.info("更新库存成功: {}", inventory);
            System.out.println(inventory);
        } else {
            log.info("库存记录不存在，无法更新");
        }
    }

    @Test
    public void testDeleteByPrimaryKey() {
        int result = inventoryMapper.deleteByPrimaryKey(1L);
        if (result > 0) {
            log.info("删除库存成功");
        } else {
            log.info("删除失败，库存记录可能不存在");
        }
    }
}

