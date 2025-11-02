package com.hyerp.controller;

import com.hyerp.common.Result;
import com.hyerp.model.Shipment;
import com.hyerp.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipment")
@CrossOrigin(origins = "*")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public Result<List<Shipment>> getShipmentList() {
        try {
            List<Shipment> shipmentList = shipmentService.getAllShipments();
            return Result.success(shipmentList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Shipment> getShipment(@PathVariable Long id) {
        try {
            Shipment shipment = shipmentService.getShipmentById(id);
            if (shipment == null) {
                return Result.error(404, "发货记录不存在");
            }
            return Result.success(shipment);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public Result<List<Shipment>> getShipmentByOrderId(@PathVariable Long orderId) {
        try {
            List<Shipment> shipmentList = shipmentService.getShipmentsByOrderId(orderId);
            return Result.success(shipmentList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result<Shipment> createShipment(@RequestBody Shipment shipment) {
        try {
            Shipment created = shipmentService.createShipment(shipment);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<Shipment> updateShipment(@PathVariable Long id, @RequestBody Shipment shipment) {
        try {
            Shipment updated = shipmentService.updateShipment(id, shipment);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteShipment(@PathVariable Long id) {
        try {
            shipmentService.deleteShipment(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

