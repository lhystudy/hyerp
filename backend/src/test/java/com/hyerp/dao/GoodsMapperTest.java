package com.hyerp.dao;

import com.hyerp.model.Goods;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
public class GoodsMapperTest {

    @Autowired
    GoodsMapper goodsMapper;

    @Test
    public void testInsert() {
        Goods goods = new Goods();
        goods.setGoodsCode("GOODS001");
        goods.setGoodsName("测试商品");
        goods.setDescription("这是一个测试商品");
        goods.setPrice(new BigDecimal("99.99"));
        goods.setUnit("件");
        goods.setCategory("电子产品");
        goods.setStatus(1);
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        goodsMapper.insert(goods);
        log.info("插入商品成功，ID: {}", goods.getId());
        System.out.println(goods);
    }

    @Test
    public void testSelectByPrimaryKey() {
        Goods goods = goodsMapper.selectByPrimaryKey(1L);
        if (goods != null) {
            log.info("查询商品成功: {}", goods);
            System.out.println(goods);
        } else {
            log.info("商品不存在");
        }
    }

    @Test
    public void testSelectAll() {
        List<Goods> goodsList = goodsMapper.selectAll();
        log.info("查询所有商品，数量: {}", goodsList.size());
        goodsList.forEach(System.out::println);
    }

    @Test
    public void testSearchByName() {
        List<Goods> goodsList = goodsMapper.searchByName("测试");
        log.info("搜索商品，数量: {}", goodsList.size());
        goodsList.forEach(System.out::println);
    }

    @Test
    public void testSelectByGoodsCode() {
        Goods goods = goodsMapper.selectByGoodsCode("GOODS001");
        if (goods != null) {
            log.info("根据商品编号查询成功: {}", goods);
            System.out.println(goods);
        } else {
            log.info("商品不存在");
        }
    }

    @Test
    public void testUpdateByPrimaryKey() {
        Goods goods = goodsMapper.selectByPrimaryKey(1L);
        if (goods != null) {
            goods.setGoodsName("更新后的商品名称");
            goods.setPrice(new BigDecimal("199.99"));
            goods.setUpdateTime(new Date());
            goodsMapper.updateByPrimaryKey(goods);
            log.info("更新商品成功: {}", goods);
            System.out.println(goods);
        } else {
            log.info("商品不存在，无法更新");
        }
    }

    @Test
    public void testDeleteByPrimaryKey() {
        int result = goodsMapper.deleteByPrimaryKey(1L);
        if (result > 0) {
            log.info("删除商品成功");
        } else {
            log.info("删除失败，商品可能不存在");
        }
    }
}

