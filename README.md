# HyErp - 电商ERP管理系统

这是一个前后端分离的电商ERP管理系统项目。前端使用Vue + Element UI构建用户界面，后端使用Spring Boot + MyBatis处理业务逻辑。

## 📋 目录

- [项目简介](#项目简介)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [功能模块](#功能模块)
- [环境要求](#环境要求)
- [快速开始](#快速开始)
- [数据库配置](#数据库配置)
- [API接口文档](#api接口文档)
- [常见问题](#常见问题)
- [开发说明](#开发说明)

## 📖 项目简介

HyErp是一个完整的电商ERP管理系统，可以帮助企业管理商品、订单、库存和发货等业务流程。系统采用前后端分离架构，便于开发和维护。

### 主要特点

- ✅ 前后端分离，职责清晰
- ✅ RESTful API设计，接口规范统一
- ✅ 用户登录认证，路由守卫保护
- ✅ 完整的CRUD操作（增删改查）
- ✅ 订单状态流转管理
- ✅ 自动生成API文档（Swagger）
- ✅ 响应式设计，界面美观

## 🛠 技术栈

### 后端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 1.8 | 编程语言 |
| Spring Boot | 2.7.18 | 后端框架 |
| MyBatis | 2.3.1 | 持久层框架 |
| MySQL | 8.0 | 数据库 |
| Maven | - | 项目构建工具 |
| Swagger | 3.0.0 | API文档生成工具 |

### 前端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 2.6.14 | 前端框架 |
| Element UI | 2.15.13 | UI组件库 |
| Vue Router | 3.5.4 | 路由管理 |
| Vuex | 3.6.2 | 状态管理 |
| Axios | 0.27.2 | HTTP请求库 |
| Sass | - | CSS预处理器 |

## 📁 项目结构

```
HyErp/
├── backend/                    # 后端项目目录
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hyerp/
│   │   │   │   ├── model/      # 实体类（对应数据库表）
│   │   │   │   ├── dao/        # MyBatis Mapper接口（数据访问层）
│   │   │   │   ├── service/    # 业务逻辑层
│   │   │   │   ├── controller/ # 控制器层（处理HTTP请求）
│   │   │   │   ├── config/     # 配置类（Swagger、跨域等）
│   │   │   │   ├── common/     # 通用类（统一响应格式等）
│   │   │   │   ├── dto/        # 数据传输对象
│   │   │   │   └── Application.java  # 启动类
│   │   │   └── resources/
│   │   │       ├── application.yml    # 配置文件
│   │   │       ├── mapper/            # MyBatis XML映射文件
│   │   │       └── db/                # 数据库SQL脚本
│   │   └── test/               # 测试代码
│   └── pom.xml                 # Maven依赖配置
│
└── frontend/                   # 前端项目目录
    ├── src/
    │   ├── api/               # API接口定义
    │   ├── views/             # 页面组件
    │   ├── router/            # 路由配置
    │   ├── store/             # Vuex状态管理
    │   ├── utils/             # 工具类（请求拦截器等）
    │   ├── layout/            # 布局组件
    │   ├── styles/            # 样式文件
    │   ├── App.vue            # 根组件
    │   └── main.js            # 入口文件
    ├── public/                # 静态资源
    ├── package.json           # 依赖配置
    └── vue.config.js          # Vue配置
```

## 🎯 功能模块

### 1. 用户登录认证
- 用户登录/登出
- 路由守卫（未登录自动跳转登录页）
- Token管理

### 2. 商品管理
- 商品列表查询
- 商品新增/编辑/删除
- 商品信息管理（编号、名称、价格等）

### 3. 订单管理
- 订单列表查询
- 订单创建（支持添加多个商品）
- 订单编辑/删除
- **订单确认**（仅待处理订单可确认）
- 订单状态管理（待处理→已确认→已预定→已发货→已完成）

### 4. 库存管理
- 库存列表查询
- 库存新增/编辑/删除
- 库存数量管理

### 5. 发货管理
- 发货单列表查询
- 发货单创建（仅可选择已预定状态的订单）
- 自动填充收货人信息（从订单获取）
- 发货人信息默认值设置
- 发货单编辑/删除

### 6. 品类管理
- 品类列表查询
- 品类新增/编辑/删除

## 💻 环境要求

在开始之前，请确保您的开发环境已安装以下软件：

### 必需软件

1. **JDK 1.8** 或更高版本
   - 验证安装：在命令行运行 `java -version`

2. **Maven 3.6+**
   - 验证安装：在命令行运行 `mvn -version`

3. **MySQL 8.0**
   - 确保MySQL服务正在运行

4. **Node.js 14+** 和 **npm 6+**
   - 验证安装：在命令行运行 `node -v` 和 `npm -v`

5. **IDE（推荐）**
   - IntelliJ IDEA（后端开发）
   - Visual Studio Code（前端开发）

## 🚀 快速开始

### 第一步：克隆项目

```bash
git clone <项目地址>
cd hyerp
```

### 第二步：数据库配置

1. **创建数据库**

打开MySQL命令行或客户端工具，执行创建数据库：

```sql
CREATE DATABASE hyerp CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
创建完数据库后使用 `backend/src/main/resources/db/hyerp.sql`中的建表语句完成表的创建

2. **修改数据库连接配置**

编辑 `backend/src/main/resources/application.yml` 文件，修改数据库连接信息：

```yaml
spring:
  profiles:
    active: dev #开发时写为dev，打包成jar包发布时修改为prod
...    
datasource:
  url: jdbc:mysql://127.0.0.1:3306/hyerp?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false
  username: root        # 修改为您的MySQL用户名
  password: 123456      # 修改为您的MySQL密码
```

3. **创建数据库表**

执行 `backend/src/main/resources/db/` 目录下的SQL脚本：

- `hyerp.sql` - 建库脚本


### 第三步：启动后端服务（可直接在idea中打开backend目录并运行本项目，无需按命令行执行）

1. **进入后端目录**

```bash
cd backend
```

2. **安装依赖（首次运行）**

```bash
mvn clean install
```

3. **启动服务**

```bash
mvn spring-boot:run
```
### 本地开发需要将application.yml中active修改为dev，datasource.password修改为自己本地数据库地址
或者使用IDE直接运行 `Application.java` 文件。

4. **验证启动**

看到类似以下日志表示启动成功：

```
Started Application in X.XXX seconds
```

后端服务默认运行在：**http://localhost:6818**

### 第四步：启动前端服务

1. **进入前端目录**

```bash
cd frontend
```

2. **安装依赖（首次运行）**

```bash
npm install
```

如果安装速度慢，可以使用国内镜像：

```bash
npm install --registry=https://registry.npmmirror.com
```

3. **启动开发服务器**

```bash
npm run dev
```

4. **访问应用**

前端应用将运行在：**http://localhost:9528**

在浏览器中打开该地址，使用默认账号登录：
- 用户名：`admin`
- 密码：`admin`

## 📊 数据库表结构说明

### 订单状态说明

| 状态值 | 状态名称 | 说明 |
|--------|----------|------|
| 0 | 待处理 | 订单刚创建，等待确认 |
| 1 | 已确认 | 订单已确认，可以进行预定操作 |
| 2 | 已预定 | 订单已预定，可以创建发货单 |
| 3 | 已发货 | 订单已发货 |
| 4 | 已完成 | 订单已完成 |
| 5 | 已取消 | 订单已取消 |

### 发货状态说明

| 状态值 | 状态名称 | 说明 |
|--------|----------|------|
| 0 | 待发货 | 发货单已创建，等待发货 |
| 1 | 已发货 | 已发货 |
| 2 | 运输中 | 运输中 |
| 3 | 已送达 | 已送达 |
| 4 | 异常 | 异常状态 |

## 📚 API接口文档

### Swagger文档

项目集成了Swagger，可以自动生成和查看API文档。

**访问地址**：http://localhost:6818/swagger-ui/index.html

在Swagger UI中，您可以：
- 查看所有API接口
- 查看接口参数说明
- 在线测试接口
- 查看接口返回示例

### 主要API接口

#### 用户认证

- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 用户登出

#### 商品管理

- `GET /api/goods` - 获取商品列表
- `GET /api/goods/{id}` - 获取商品详情
- `POST /api/goods` - 创建商品
- `PUT /api/goods/{id}` - 更新商品
- `DELETE /api/goods/{id}` - 删除商品

#### 订单管理

- `GET /api/order` - 获取订单列表（支持status参数筛选）
- `GET /api/order/{id}` - 获取订单详情
- `POST /api/order` - 创建订单
- `PUT /api/order/{id}` - 更新订单
- `PUT /api/order/{id}/confirm` - 确认订单（将状态改为已确认）
- `DELETE /api/order/{id}` - 删除订单

#### 库存管理

- `GET /api/inventory` - 获取库存列表
- `GET /api/inventory/{id}` - 获取库存详情
- `POST /api/inventory` - 创建库存记录
- `PUT /api/inventory/{id}` - 更新库存记录
- `DELETE /api/inventory/{id}` - 删除库存记录

#### 发货管理

- `GET /api/shipment` - 获取发货列表
- `GET /api/shipment/{id}` - 获取发货详情
- `GET /api/shipment/order/{orderId}` - 根据订单ID获取发货记录
- `POST /api/shipment` - 创建发货记录
- `PUT /api/shipment/{id}` - 更新发货记录
- `DELETE /api/shipment/{id}` - 删除发货记录

#### 品类管理

- `GET /api/category` - 获取品类列表
- `GET /api/category/{id}` - 获取品类详情
- `POST /api/category` - 创建品类
- `PUT /api/category/{id}` - 更新品类
- `DELETE /api/category/{id}` - 删除品类

### 统一响应格式

所有API接口都返回统一的响应格式：

```json
{
  "code": 200,           // 状态码，200表示成功，其他值表示失败
  "message": "操作成功",  // 提示信息
  "data": {}             // 数据内容
}
```

## ❓ 常见问题

### 1. 后端启动失败

**问题**：启动时提示数据库连接失败

**解决方案**：
- 检查MySQL服务是否启动
- 检查 `application.yml` 中的数据库连接信息是否正确
- 确认数据库 `hyerp` 是否已创建

### 2. 前端启动失败

**问题**：`npm install` 失败或速度很慢

**解决方案**：
- 使用国内镜像：`npm install --registry=https://registry.npmmirror.com`
- 清除缓存：`npm cache clean --force`
- 删除 `node_modules` 文件夹后重新安装

### 3. 登录后无法访问页面

**问题**：登录成功但跳转后显示空白或错误

**解决方案**：
- 检查后端服务是否正常运行
- 检查浏览器控制台是否有错误信息
- 确认前端请求的API地址是否正确（默认：`/api`）

### 4. 跨域问题

**问题**：前端请求后端接口时出现CORS错误

**解决方案**：
- 后端已配置跨域支持，检查 `CorsConfig.java` 是否正确
- 确认后端服务端口是否为6818

### 5. Swagger无法访问

**问题**：访问 http://localhost:6818/swagger-ui/index.html 显示404

**解决方案**：
- 确认后端服务已启动
- 检查 `SwaggerConfig.java` 配置是否正确
- 尝试访问：http://localhost:6818/swagger-ui.html

## 🔧 开发说明

### 代码规范

1. **后端**
   - 使用Lombok简化代码
   - Controller层只处理HTTP请求，业务逻辑放在Service层
   - 统一使用Result类封装响应

2. **前端**
   - 组件化开发，每个页面一个组件
   - API请求统一放在 `api` 目录
   - 使用Element UI组件库

### 添加新功能

1. **后端**
   - 在 `model` 包创建实体类
   - 在 `dao` 包创建Mapper接口
   - 在 `resources/mapper` 创建XML映射文件
   - 在 `service` 包创建Service类
   - 在 `controller` 包创建Controller类

2. **前端**
   - 在 `api` 目录添加API方法
   - 在 `views` 目录创建页面组件
   - 在 `router/index.js` 添加路由配置

### 调试技巧

1. **后端调试**
   - 使用IDE断点调试
   - 查看控制台日志
   - 使用Swagger测试接口

2. **前端调试**
   - 使用浏览器开发者工具
   - 查看Network面板检查API请求
   - 查看Console面板查看错误信息


## 📄 License

MIT License

## 常用命令
### java 打包命令
```mvn clean package -DskipTests```
### 前端相关命令
#### 测试运行
```npm run dev```
#### 打包命令，打包后输出的文件在./dist目录下
```npm run build```