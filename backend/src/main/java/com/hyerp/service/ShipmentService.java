package com.hyerp.service;

import com.hyerp.dao.ShipmentMapper;
import com.hyerp.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentMapper shipmentMapper;

    public List<Shipment> getAllShipments() {
        return shipmentMapper.selectAll();
    }

    public Shipment getShipmentById(Long id) {
        return shipmentMapper.selectByPrimaryKey(id);
    }

    public List<Shipment> getShipmentsByOrderId(Long orderId) {
        return shipmentMapper.selectByOrderId(orderId);
    }

    public Shipment createShipment(Shipment shipment) {
        // 生成发货单号
        if (shipment.getShipmentNo() == null || shipment.getShipmentNo().isEmpty()) {
            shipment.setShipmentNo("SHIP" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (shipment.getStatus() == null) {
            shipment.setStatus(0); // 默认待发货
        }
        shipment.setCreateTime(new Date());
        shipment.setUpdateTime(new Date());
        shipmentMapper.insert(shipment);
        return shipment;
    }

    public Shipment updateShipment(Long id, Shipment shipment) {
        Shipment existing = shipmentMapper.selectByPrimaryKey(id);
        if (existing == null) {
            throw new RuntimeException("发货记录不存在");
        }
        shipment.setId(id);
        shipment.setUpdateTime(new Date());
        shipmentMapper.updateByPrimaryKey(shipment);
        return shipment;
    }

    public void deleteShipment(Long id) {
        Shipment shipment = shipmentMapper.selectByPrimaryKey(id);
        if (shipment == null) {
            throw new RuntimeException("发货记录不存在");
        }
        shipmentMapper.deleteByPrimaryKey(id);
    }
}

