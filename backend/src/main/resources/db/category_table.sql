-- 品类表
CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_code VARCHAR(100) NOT NULL UNIQUE COMMENT '品类编码',
    category_name VARCHAR(200) NOT NULL COMMENT '品类名称',
    description VARCHAR(500) COMMENT '描述',
    status INT NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    INDEX `idx_category_code`(`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='品类表';

-- 修改商品表，将category字段改为category_id
ALTER TABLE goods 
    CHANGE COLUMN `category` `category_id` BIGINT COMMENT '品类ID，关联category表';

-- 添加外键约束（可选，如果不需要外键约束可以不加）
-- ALTER TABLE goods 
--     ADD CONSTRAINT `fk_goods_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

