package com.hyerp.service;

import com.hyerp.dao.InventoryMapper;
import com.hyerp.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    public List<Inventory> getAllInventories() {
        return inventoryMapper.selectAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryMapper.selectByPrimaryKey(id);
    }

    public List<Inventory> getInventoriesByGoodsId(Long goodsId) {
        return inventoryMapper.selectByGoodsId(goodsId);
    }

    public Inventory createInventory(Inventory inventory) {
        inventory.setCreateTime(new Date());
        inventory.setUpdateTime(new Date());
        inventoryMapper.insert(inventory);
        return inventory;
    }

    public Inventory updateInventory(Long id, Inventory inventory) {
        Inventory existing = inventoryMapper.selectByPrimaryKey(id);
        if (existing == null) {
            throw new RuntimeException("库存记录不存在");
        }
        inventory.setId(id);
        inventory.setUpdateTime(new Date());
        inventoryMapper.updateByPrimaryKey(inventory);
        return inventory;
    }

    public void deleteInventory(Long id) {
        Inventory inventory = inventoryMapper.selectByPrimaryKey(id);
        if (inventory == null) {
            throw new RuntimeException("库存记录不存在");
        }
        inventoryMapper.deleteByPrimaryKey(id);
    }
}

