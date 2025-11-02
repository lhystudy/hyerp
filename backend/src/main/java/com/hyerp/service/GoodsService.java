package com.hyerp.service;

import com.hyerp.dao.CategoryMapper;
import com.hyerp.dao.GoodsMapper;
import com.hyerp.model.Category;
import com.hyerp.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Goods> getAllGoods() {
        return goodsMapper.selectAll();
    }

    public Goods getGoodsById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    public List<Goods> searchGoodsByName(String name) {
        return goodsMapper.searchByName(name);
    }

    public Goods createGoods(Goods goods) {
        // 检查商品编号是否已存在
        Goods existing = goodsMapper.selectByGoodsCode(goods.getGoodsCode());
        if (existing != null) {
            throw new RuntimeException("商品编号已存在");
        }
        // 验证品类ID是否存在
        if (goods.getCategoryId() != null) {
            Category category = categoryMapper.selectByPrimaryKey(goods.getCategoryId());
            if (category == null) {
                throw new RuntimeException("品类不存在");
            }
            if (category.getStatus() != 1) {
                throw new RuntimeException("所选品类已停用");
            }
        }
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        if (goods.getStatus() == null) {
            goods.setStatus(1); // 默认启用
        }
        goodsMapper.insert(goods);
        return goods;
    }

    public Goods updateGoods(Long id, Goods goods) {
        Goods existing = goodsMapper.selectByPrimaryKey(id);
        if (existing == null) {
            throw new RuntimeException("商品不存在");
        }
        // 如果修改了商品编号，检查新编号是否已存在
        if (!existing.getGoodsCode().equals(goods.getGoodsCode())) {
            Goods codeExists = goodsMapper.selectByGoodsCode(goods.getGoodsCode());
            if (codeExists != null) {
                throw new RuntimeException("商品编号已存在");
            }
        }
        // 验证品类ID是否存在
        if (goods.getCategoryId() != null) {
            Category category = categoryMapper.selectByPrimaryKey(goods.getCategoryId());
            if (category == null) {
                throw new RuntimeException("品类不存在");
            }
            if (category.getStatus() != 1) {
                throw new RuntimeException("所选品类已停用");
            }
        }
        goods.setId(id);
        goods.setUpdateTime(new Date());
        goodsMapper.updateByPrimaryKey(goods);
        return goods;
    }

    public void deleteGoods(Long id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
        goodsMapper.deleteByPrimaryKey(id);
    }
}

