# HyErp - ERP管理系统

这是一个前后端分离的ERP管理系统项目，前端使用Vue + Element UI，后端使用Spring Boot + MyBatis。

## 项目结构

```
HyErp/
├── backend/          # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hyerp/
│   │   │   │   ├── entity/      # 实体类
│   │   │   │   ├── mapper/      # MyBatis Mapper接口
│   │   │   │   ├── service/     # 业务逻辑层
│   │   │   │   ├── controller/  # 控制器层
│   │   │   │   ├── config/      # 配置类
│   │   │   │   └── common/      # 通用类
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── mapper/       # MyBatis XML映射文件
│   │   └── pom.xml
└── frontend/         # 前端项目
    ├── src/
    │   ├── api/      # API接口
    │   ├── views/    # 页面组件
    │   ├── router/   # 路由配置
    │   ├── store/    # 状态管理
    │   ├── utils/    # 工具类
    │   └── layout/   # 布局组件
    └── package.json
```

## 技术栈

### 后端
- Java 1.8
- Spring Boot 2.7.18
- MyBatis 2.3.1
- MySQL 8.0
- Maven

### 前端
- Vue 2.6.14
- Element UI 2.15.13
- Vue Router 3.5.4
- Vuex 3.6.2
- Axios 0.27.2
- Sass (样式预处理器)

## 主要功能模块

1. **商品管理** - 商品的增删改查
2. **订单管理** - 订单的创建、查询、状态管理
3. **库存管理** - 库存数量的管理
4. **发货管理** - 发货单的管理和物流跟踪

## 数据库表结构

### goods (商品表)
- id: 主键
- goodsCode: 商品编号
- goodsName: 商品名称
- price: 价格
- unit: 单位
- category: 分类
- status: 状态 (0-停用, 1-启用)

### order (订单表)
- id: 主键
- orderNo: 订单号
- customerName: 客户名称
- customerPhone: 客户电话
- totalAmount: 总金额
- status: 状态 (0-待处理, 1-已确认, 2-已发货, 3-已完成, 4-已取消)

### inventory (库存表)
- id: 主键
- goodsId: 商品ID
- quantity: 库存数量
- warehouseName: 仓库名称
- warehouseLocation: 仓库位置
- minStock: 最小库存
- maxStock: 最大库存

### shipment (发货表)
- id: 主键
- orderId: 订单ID
- shipmentNo: 发货单号
- receiverName: 收货人
- receiverPhone: 收货电话
- status: 状态 (0-待发货, 1-已发货, 2-运输中, 3-已送达, 4-异常)
- trackingNumber: 物流单号

## 快速开始

### 后端启动

1. 确保已安装JDK 1.8和Maven
2. 创建MySQL数据库：
```sql
CREATE DATABASE hyerp CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. 修改 `backend/src/main/resources/application.yml` 中的数据库连接信息

4. 数据库表会自动创建（如果不存在），或者您可以手动创建表结构

5. 进入backend目录，运行：
```bash
mvn spring-boot:run
```

后端服务将运行在 http://localhost:8080/api

**注意**：MyBatis不会自动创建表，您需要手动创建数据库表。执行以下SQL脚本创建表结构：

```sql
-- 商品表
CREATE TABLE goods (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    goods_code VARCHAR(100) NOT NULL UNIQUE COMMENT '商品编号',
    goods_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    description VARCHAR(500) COMMENT '描述',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    unit VARCHAR(50) COMMENT '单位',
    category VARCHAR(100) COMMENT '分类',
    status INT NOT NULL DEFAULT 1 COMMENT '状态 0-停用 1-启用',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 订单表
CREATE TABLE `order` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
    customer_name VARCHAR(100) COMMENT '客户名称',
    customer_phone VARCHAR(20) COMMENT '客户电话',
    customer_address VARCHAR(500) COMMENT '客户地址',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '总金额',
    status INT NOT NULL DEFAULT 0 COMMENT '状态 0-待处理 1-已确认 2-已发货 3-已完成 4-已取消',
    order_date DATETIME NOT NULL COMMENT '订单日期',
    delivery_date DATETIME COMMENT '配送日期',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 库存表
CREATE TABLE inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    quantity INT NOT NULL COMMENT '库存数量',
    warehouse_name VARCHAR(100) COMMENT '仓库名称',
    warehouse_location VARCHAR(200) COMMENT '仓库位置',
    min_stock INT COMMENT '最小库存',
    max_stock INT COMMENT '最大库存',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';

-- 发货表
CREATE TABLE shipment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    shipment_no VARCHAR(50) NOT NULL UNIQUE COMMENT '发货单号',
    shipper_name VARCHAR(100) COMMENT '发货人姓名',
    shipper_phone VARCHAR(20) COMMENT '发货人电话',
    shipping_address VARCHAR(500) COMMENT '发货地址',
    receiver_name VARCHAR(100) COMMENT '收货人姓名',
    receiver_phone VARCHAR(20) COMMENT '收货人电话',
    receiver_address VARCHAR(500) COMMENT '收货地址',
    status INT NOT NULL DEFAULT 0 COMMENT '状态 0-待发货 1-已发货 2-运输中 3-已送达 4-异常',
    ship_date DATETIME COMMENT '发货日期',
    expected_delivery_date DATETIME COMMENT '预计送达日期',
    actual_delivery_date DATETIME COMMENT '实际送达日期',
    tracking_number VARCHAR(100) COMMENT '物流单号',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发货表';
```

### 前端启动

1. 确保已安装Node.js和npm
2. 进入frontend目录，安装依赖：
```bash
npm install
```

3. 启动开发服务器：
```bash
npm run dev
```

前端应用将运行在 http://localhost:9528

## API接口

### 商品管理
- GET `/api/goods` - 获取商品列表
- GET `/api/goods/{id}` - 获取商品详情
- POST `/api/goods` - 创建商品
- PUT `/api/goods/{id}` - 更新商品
- DELETE `/api/goods/{id}` - 删除商品

### 订单管理
- GET `/api/order` - 获取订单列表
- GET `/api/order/{id}` - 获取订单详情
- POST `/api/order` - 创建订单
- PUT `/api/order/{id}` - 更新订单
- DELETE `/api/order/{id}` - 删除订单

### 库存管理
- GET `/api/inventory` - 获取库存列表
- GET `/api/inventory/{id}` - 获取库存详情
- POST `/api/inventory` - 创建库存记录
- PUT `/api/inventory/{id}` - 更新库存记录
- DELETE `/api/inventory/{id}` - 删除库存记录

### 发货管理
- GET `/api/shipment` - 获取发货列表
- GET `/api/shipment/{id}` - 获取发货详情
- POST `/api/shipment` - 创建发货记录
- PUT `/api/shipment/{id}` - 更新发货记录
- DELETE `/api/shipment/{id}` - 删除发货记录

## 注意事项

1. 确保MySQL数据库已创建并运行
2. 后端会自动创建数据表（通过JPA的ddl-auto配置）
3. 前后端都配置了跨域支持
4. 登录功能目前为简单实现，生产环境需要添加真实的认证授权

## 开发说明

- 后端采用RESTful API设计
- 前端采用组件化开发
- 统一使用Result类封装API响应
- 使用Element UI组件库构建界面

## License

MIT

