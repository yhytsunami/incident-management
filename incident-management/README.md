## 总体设计

**结构**:

- **RESTful API**: 使用 RESTful 风格设计 API。
- **层级**: 由前端mvc+服务+持久层构成。

**应用技术和工具**:

- **语言框架**: Java 17,  Spring Boot，Spring Data JPA，
- **项目管理工具**: Maven 3.6.0,
- **部署容器**: Docker,

**数据设计**：

数据库：轻量级数据库H2 

实体: IncidentEntity

字段 :

- `id`: 主键

- `name`: 名称

- `description`: 描述

- `status`: 状态

- `created time`: 创建时间

- `updated time`: 修改时间


**API**:
- 1.事件新增
- post /api/incidents/incident
  
- 2.事件更新
- put /api/incidents/{id}

- 3.事件删除
- delete /api/incidents/{id}

- 4.事件列表查询
- get /api/incidents

**验证和异常处理**:

- 利用spring 实现jsr 303验证框架。
- 利用spring controller advice统一异常处理。

 **缓存机制**：

- 使用Spring Cache， 配置caffeine缓存。


 **测试**：

  1、单元测试：
      使用 JUnit 和 Mockito 进行单元测试，集成jacoco生成测试报告。

  2、压力测试：
      使用jmh 执行benchmark进行接口压力测试。

  **部署容器**：

  - Docker: 配置 Dockerfile 构建镜像部署。

