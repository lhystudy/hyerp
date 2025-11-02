package com.hyerp.controller;

import com.hyerp.common.Result;
import com.hyerp.model.Goods;
import com.hyerp.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
@CrossOrigin(origins = "*")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping
    public Result<List<Goods>> getGoodsList() {
        try {
            List<Goods> goodsList = goodsService.getAllGoods();
            return Result.success(goodsList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Goods> getGoods(@PathVariable Long id) {
        try {
            Goods goods = goodsService.getGoodsById(id);
            if (goods == null) {
                return Result.error(404, "商品不存在");
            }
            return Result.success(goods);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/search")
    public Result<List<Goods>> searchGoods(@RequestParam String name) {
        try {
            List<Goods> goodsList = goodsService.searchGoodsByName(name);
            return Result.success(goodsList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result<Goods> createGoods(@RequestBody Goods goods) {
        try {
            Goods created = goodsService.createGoods(goods);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<Goods> updateGoods(@PathVariable Long id, @RequestBody Goods goods) {
        try {
            Goods updated = goodsService.updateGoods(id, goods);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteGoods(@PathVariable Long id) {
        try {
            goodsService.deleteGoods(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

