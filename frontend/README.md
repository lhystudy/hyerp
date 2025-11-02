# ERP前端项目

基于Vue 2 + Element UI的后台管理系统前端项目。

## 技术栈

- Vue 2.6.14
- Vue Router 3.5.4
- Vuex 3.6.2
- Element UI 2.15.13
- Axios 0.27.2

## 安装依赖

```bash
npm install
```

## 开发

```bash
npm run dev
```

前端应用将运行在 http://localhost:9528

## 构建

```bash
npm run build
```

构建后的文件将输出到 `dist` 目录。

## 项目结构

```
frontend/
├── public/          # 静态资源
├── src/
│   ├── api/         # API接口定义
│   ├── assets/      # 资源文件
│   ├── components/  # 公共组件
│   ├── layout/     # 布局组件
│   ├── router/      # 路由配置
│   ├── store/       # Vuex状态管理
│   ├── styles/      # 样式文件
│   ├── utils/       # 工具函数
│   ├── views/       # 页面组件
│   ├── App.vue      # 根组件
│   └── main.js      # 入口文件
├── vue.config.js    # Vue CLI配置
└── package.json     # 项目依赖
```

