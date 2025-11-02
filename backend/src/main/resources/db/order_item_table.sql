-- 订单明细表
CREATE TABLE order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    quantity INT NOT NULL COMMENT '商品数量',
    price DECIMAL(10,2) NOT NULL COMMENT '单价（下单时的商品价格）',
    subtotal DECIMAL(10,2) NOT NULL COMMENT '小计（price * quantity）',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    INDEX `idx_order_id`(`order_id`),
    INDEX `idx_goods_id`(`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- 添加外键约束（可选，如果不需要外键约束可以不加）
-- ALTER TABLE order_item 
--     ADD CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
--     ADD CONSTRAINT `fk_order_item_goods` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`);

