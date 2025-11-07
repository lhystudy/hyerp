/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : 127.0.0.1:3306
 Source Schema         : hyerp

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 07/11/2025 11:06:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `category_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品类编码',
  `category_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品类名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
  `sort_order` int(0) NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `category_code`(`category_code`) USING BTREE,
  INDEX `idx_category_code`(`category_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '品类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '1', 'T恤', '', 1, 1, '2025-11-02 12:07:02', '2025-11-02 12:07:02');
INSERT INTO `category` VALUES (2, '2', '长裤', '', 1, 2, '2025-11-02 12:07:14', '2025-11-02 12:07:14');
INSERT INTO `category` VALUES (3, '3', '九分裤', '', 1, 3, '2025-11-02 12:07:25', '2025-11-02 12:07:25');
INSERT INTO `category` VALUES (4, '4', '长裙', '', 1, 4, '2025-11-02 12:07:37', '2025-11-02 12:07:37');
INSERT INTO `category` VALUES (5, '5', '过膝裙', '', 1, 5, '2025-11-02 12:07:53', '2025-11-02 12:07:53');
INSERT INTO `category` VALUES (6, '6', '短裙', '', 1, 6, '2025-11-02 12:08:03', '2025-11-02 12:08:03');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `goods_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品编号',
  `goods_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单位',
  `category_id` bigint(0) NULL DEFAULT NULL COMMENT '品类ID，关联category表',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `goods_code`(`goods_code`) USING BTREE,
  INDEX `idx_goods_code`(`goods_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, 'G01', '牛仔长裤-女', '', 80.00, '件', 2, 1, '2025-11-02 12:08:45', '2025-11-02 12:13:30');
INSERT INTO `goods` VALUES (2, 'G02', '牛仔长裤-男', '', 78.00, '件', 2, 1, '2025-11-02 12:13:20', '2025-11-02 12:13:20');

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(0) NOT NULL COMMENT '商品ID',
  `quantity` int(0) NOT NULL COMMENT '库存数量',
  `warehouse_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `warehouse_location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库位置',
  `min_stock` int(0) NULL DEFAULT NULL COMMENT '最小库存',
  `max_stock` int(0) NULL DEFAULT NULL COMMENT '最大库存',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_goods_id`(`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '库存表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inventory
-- ----------------------------
INSERT INTO `inventory` VALUES (2, 1, 2, '上海发货仓', '上海浦东新区', 0, 99999, '', '2025-11-02 12:12:24', '2025-11-07 10:41:16');
INSERT INTO `inventory` VALUES (3, 2, 2, '上海发货仓', '上海浦东新区', 0, 99999, '', '2025-11-06 16:15:54', '2025-11-07 10:41:13');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户名称',
  `customer_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户电话',
  `customer_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户地址',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '总金额',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '状态 0-待处理 1-已确认 2-已发货 3-已完成 4-已取消',
  `order_date` datetime(0) NOT NULL COMMENT '订单日期',
  `delivery_date` datetime(0) NULL DEFAULT NULL COMMENT '配送日期',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_no`(`order_no`) USING BTREE,
  INDEX `idx_order_no`(`order_no`) USING BTREE,
  INDEX `idx_phone`(`customer_phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 'ORD1762052995800', '张三', '13800138000', '北京市朝阳区测试街道123号', 299.99, 2, '2025-11-02 11:09:56', NULL, '测试订单', '2025-11-02 11:09:56', '2025-11-06 16:34:54');
INSERT INTO `order` VALUES (2, 'ORD176205741822412C4806F', '六喜喜', '12738219232', '上海市浦东新区111号', 396.00, 2, '2025-11-02 12:23:38', NULL, '', '2025-11-02 12:23:38', '2025-11-02 16:40:34');
INSERT INTO `order` VALUES (3, 'ORD1762417939015E3127429', '伍六一', '1111111', 'Testest', 472.00, 2, '2025-11-06 16:32:19', NULL, '', '2025-11-06 16:32:19', '2025-11-06 17:21:06');
INSERT INTO `order` VALUES (4, 'ORD1762479163367D070B2AC', '信息', '111', 'djesijf', 554.00, 2, '2025-11-07 09:32:43', NULL, '', '2025-11-07 09:32:43', '2025-11-07 09:36:45');
INSERT INTO `order` VALUES (5, 'ORD17624794534441BDC79EF', 'aaa', '1111', '12211', 628.00, 2, '2025-11-07 09:37:33', NULL, '', '2025-11-07 09:37:33', '2025-11-07 09:41:51');
INSERT INTO `order` VALUES (6, 'ORD1762483250930B3A8ED13', 'ddddd', '11111', '地址地址', 474.00, 3, '2025-11-07 10:40:51', NULL, '', '2025-11-07 10:40:51', '2025-11-07 10:41:16');

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(0) NOT NULL COMMENT '订单ID',
  `goods_id` bigint(0) NOT NULL COMMENT '商品ID',
  `quantity` int(0) NOT NULL COMMENT '商品数量',
  `price` decimal(10, 2) NOT NULL COMMENT '单价（下单时的商品价格）',
  `subtotal` decimal(10, 2) NOT NULL COMMENT '小计（price * quantity）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE,
  INDEX `idx_goods_id`(`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (1, 2, 2, 2, 78.00, 156.00, '2025-11-02 12:23:38', '2025-11-02 12:23:38');
INSERT INTO `order_item` VALUES (2, 2, 1, 3, 80.00, 240.00, '2025-11-02 12:23:38', '2025-11-02 12:23:38');
INSERT INTO `order_item` VALUES (3, 3, 2, 4, 78.00, 312.00, '2025-11-06 16:32:19', '2025-11-06 16:32:19');
INSERT INTO `order_item` VALUES (4, 3, 1, 2, 80.00, 160.00, '2025-11-06 16:32:19', '2025-11-06 16:32:19');
INSERT INTO `order_item` VALUES (5, 4, 2, 3, 78.00, 234.00, '2025-11-07 09:32:43', '2025-11-07 09:32:43');
INSERT INTO `order_item` VALUES (6, 4, 1, 4, 80.00, 320.00, '2025-11-07 09:32:43', '2025-11-07 09:32:43');
INSERT INTO `order_item` VALUES (7, 5, 1, 2, 80.00, 160.00, '2025-11-07 09:37:33', '2025-11-07 09:37:33');
INSERT INTO `order_item` VALUES (8, 5, 2, 6, 78.00, 468.00, '2025-11-07 09:37:33', '2025-11-07 09:37:33');
INSERT INTO `order_item` VALUES (9, 6, 2, 3, 78.00, 234.00, '2025-11-07 10:40:51', '2025-11-07 10:40:51');
INSERT INTO `order_item` VALUES (10, 6, 1, 3, 80.00, 240.00, '2025-11-07 10:40:51', '2025-11-07 10:40:51');

-- ----------------------------
-- Table structure for order_item_reserved
-- ----------------------------
DROP TABLE IF EXISTS `order_item_reserved`;
CREATE TABLE `order_item_reserved`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(0) NOT NULL COMMENT 'order_item_id',
  `inventory_id` bigint(0) NOT NULL DEFAULT 0 COMMENT 'inventory_id',
  `reserved_quantity` int(0) NOT NULL DEFAULT 0 COMMENT '已预定数量',
  `needed_quantity` int(0) NOT NULL DEFAULT 0 COMMENT '需预定数量',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_item_id`(`item_id`) USING BTREE,
  INDEX `idx_inventory_id`(`inventory_id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE,
  INDEX `idx_update_time`(`update_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单预定明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item_reserved
-- ----------------------------
INSERT INTO `order_item_reserved` VALUES (1, 1, 3, 2, 2, '2025-11-06 16:13:33', '2025-11-06 16:16:00');
INSERT INTO `order_item_reserved` VALUES (2, 2, 2, 3, 3, '2025-11-06 16:16:00', '2025-11-06 16:16:00');
INSERT INTO `order_item_reserved` VALUES (5, 3, 3, 4, 4, '2025-11-06 17:18:02', '2025-11-06 17:21:06');
INSERT INTO `order_item_reserved` VALUES (6, 4, 2, 2, 2, '2025-11-06 17:21:06', '2025-11-06 17:21:06');
INSERT INTO `order_item_reserved` VALUES (7, 5, 3, 3, 3, '2025-11-07 09:34:32', '2025-11-07 09:35:37');
INSERT INTO `order_item_reserved` VALUES (8, 6, 2, 4, 4, '2025-11-07 09:35:56', '2025-11-07 09:36:40');
INSERT INTO `order_item_reserved` VALUES (9, 7, 2, 2, 2, '2025-11-07 09:37:53', '2025-11-07 09:40:52');
INSERT INTO `order_item_reserved` VALUES (10, 8, 3, 6, 6, '2025-11-07 09:38:24', '2025-11-07 09:41:51');
INSERT INTO `order_item_reserved` VALUES (11, 9, 3, 3, 3, '2025-11-07 10:40:55', '2025-11-07 10:41:13');
INSERT INTO `order_item_reserved` VALUES (12, 10, 2, 3, 3, '2025-11-07 10:40:55', '2025-11-07 10:41:16');

-- ----------------------------
-- Table structure for shipment
-- ----------------------------
DROP TABLE IF EXISTS `shipment`;
CREATE TABLE `shipment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(0) NOT NULL COMMENT '订单ID',
  `shipment_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发货单号',
  `shipper_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发货人姓名',
  `shipper_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发货人电话',
  `shipping_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发货地址',
  `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货地址',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '状态 0-待发货 1-已发货 2-运输中 3-已送达 4-异常',
  `ship_date` datetime(0) NULL DEFAULT NULL COMMENT '发货日期',
  `expected_delivery_date` datetime(0) NULL DEFAULT NULL COMMENT '预计送达日期',
  `actual_delivery_date` datetime(0) NULL DEFAULT NULL COMMENT '实际送达日期',
  `tracking_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物流单号',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `shipment_no`(`shipment_no`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE,
  INDEX `idx_shipment_no`(`shipment_no`) USING BTREE,
  INDEX `idx_receiver_phone`(`receiver_phone`) USING BTREE,
  INDEX `idx_tracking_number`(`tracking_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发货表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shipment
-- ----------------------------
INSERT INTO `shipment` VALUES (5, 6, '11111', '上海浦东发货仓', '11111', '上海浦东新区8888号', 'ddddd', '11111', '地址地址', 0, NULL, NULL, NULL, '', '', '2025-11-07 10:48:06', '2025-11-07 10:48:06');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '管理员', NULL, NULL, 1, '2025-11-07 10:09:59', '2025-11-07 10:09:59');

SET FOREIGN_KEY_CHECKS = 1;
