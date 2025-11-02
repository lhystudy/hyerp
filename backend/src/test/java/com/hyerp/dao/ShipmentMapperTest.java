package com.hyerp.dao;

import com.hyerp.model.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
public class ShipmentMapperTest {

    @Autowired
    ShipmentMapper shipmentMapper;

    @Test
    public void testInsert() {
        Shipment shipment = new Shipment();
        shipment.setOrderId(1L);
        shipment.setShipmentNo("SHIP" + System.currentTimeMillis());
        shipment.setShipperName("发货人");
        shipment.setShipperPhone("13900139000");
        shipment.setShippingAddress("北京市朝阳区发货地址");
        shipment.setReceiverName("收货人");
        shipment.setReceiverPhone("13700137000");
        shipment.setReceiverAddress("上海市浦东新区收货地址");
        shipment.setStatus(0);
        shipment.setShipDate(new Date());
        shipment.setExpectedDeliveryDate(new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000L));
        shipment.setTrackingNumber("TRACK001");
        shipment.setRemark("测试发货");
        shipment.setCreateTime(new Date());
        shipment.setUpdateTime(new Date());
        shipmentMapper.insert(shipment);
        log.info("插入发货记录成功，ID: {}", shipment.getId());
        System.out.println(shipment);
    }

    @Test
    public void testSelectByPrimaryKey() {
        Shipment shipment = shipmentMapper.selectByPrimaryKey(1L);
        if (shipment != null) {
            log.info("查询发货记录成功: {}", shipment);
            System.out.println(shipment);
        } else {
            log.info("发货记录不存在");
        }
    }

    @Test
    public void testSelectAll() {
        List<Shipment> shipmentList = shipmentMapper.selectAll();
        log.info("查询所有发货记录，数量: {}", shipmentList.size());
        shipmentList.forEach(System.out::println);
    }

    @Test
    public void testSelectByOrderId() {
        List<Shipment> shipmentList = shipmentMapper.selectByOrderId(1L);
        log.info("根据订单ID查询发货记录，数量: {}", shipmentList.size());
        shipmentList.forEach(System.out::println);
    }

    @Test
    public void testUpdateByPrimaryKey() {
        Shipment shipment = shipmentMapper.selectByPrimaryKey(1L);
        if (shipment != null) {
            shipment.setStatus(1);
            shipment.setShipDate(new Date());
            shipment.setTrackingNumber("TRACK002");
            shipment.setUpdateTime(new Date());
            shipmentMapper.updateByPrimaryKey(shipment);
            log.info("更新发货记录成功: {}", shipment);
            System.out.println(shipment);
        } else {
            log.info("发货记录不存在，无法更新");
        }
    }

    @Test
    public void testDeleteByPrimaryKey() {
        int result = shipmentMapper.deleteByPrimaryKey(1L);
        if (result > 0) {
            log.info("删除发货记录成功");
        } else {
            log.info("删除失败，发货记录可能不存在");
        }
    }
}

