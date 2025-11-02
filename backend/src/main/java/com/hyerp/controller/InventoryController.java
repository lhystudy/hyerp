package com.hyerp.controller;

import com.hyerp.common.Result;
import com.hyerp.model.Inventory;
import com.hyerp.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public Result<List<Inventory>> getInventoryList() {
        try {
            List<Inventory> inventoryList = inventoryService.getAllInventories();
            return Result.success(inventoryList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Inventory> getInventory(@PathVariable Long id) {
        try {
            Inventory inventory = inventoryService.getInventoryById(id);
            if (inventory == null) {
                return Result.error(404, "库存记录不存在");
            }
            return Result.success(inventory);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/goods/{goodsId}")
    public Result<List<Inventory>> getInventoryByGoodsId(@PathVariable Long goodsId) {
        try {
            List<Inventory> inventoryList = inventoryService.getInventoriesByGoodsId(goodsId);
            return Result.success(inventoryList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result<Inventory> createInventory(@RequestBody Inventory inventory) {
        try {
            Inventory created = inventoryService.createInventory(inventory);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        try {
            Inventory updated = inventoryService.updateInventory(id, inventory);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteInventory(@PathVariable Long id) {
        try {
            inventoryService.deleteInventory(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

