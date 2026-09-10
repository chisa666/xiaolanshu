# xiaolanshu

基于教程逐步整理的全栈微服务练习项目，项目名为 `xiaolanshu`，作者署名为 `chisa`。

## 目录结构

- `src/main/java/com/quanxiaoha/framework`：通用响应、异常、上下文、操作日志与工具类。
- `src/main/java/com/quanxiaoha/xiaolanshu/*`：认证、网关、对象存储、用户、KV、笔记、关系、计数、搜索、评论、数据对齐等教程服务代码。
- `src/main/resources/db/schema.sql`：教程中整理出的 MySQL 表结构。
- `src/main/resources/mapper`：MyBatis Mapper XML。
- `src/main/java/com/quanxiaoha/xiaolanshu/app`：可直接启动的本地开发主链路，便于不启动全部微服务时联调登录和笔记接口。

## 环境

- JDK 17
- Maven 3.9+
- MySQL 8.x：连接信息通过 `DB_URL`、`DB_USERNAME`、`DB_PASSWORD` 配置
- Redis：连接信息通过 `REDIS_HOST`、`REDIS_PORT`、`REDIS_PASSWORD` 配置
- RocketMQ：NameServer `127.0.0.1:9876`

## 常用开发工具

- **Apipost**：接口调试地址为 `http://localhost:8080`，登录接口返回的 token 放入请求头 `Authorization: Bearer <token>`。
- **Another Redis Desktop Manager**：使用本地 `.env` 中的 Redis 连接参数，数据库选择 `0`。
- **Navicat for MySQL**：使用本地 `.env` 中的 MySQL 连接参数。

Redis 和 RocketMQ 可以使用项目根目录的 `docker-compose.yml` 启动：

```bash
docker compose --env-file .env up -d redis rocketmq-namesrv rocketmq-broker rocketmq-dashboard
```

若 Docker Desktop 尚未启动，请先启动 Docker Desktop，再执行上述命令。

## 运行

在 IDEA 中以 Maven 项目导入根目录，使用 JDK 17，刷新依赖后启动：

```bash
mvn spring-boot:run
```

可直接启动类：`com.quanxiaoha.xiaolanshu.XiaolanshuApplication`。

启动后：

```bash
# 获取验证码
curl -X POST http://localhost:8080/api/auth/send-code -H "Content-Type: application/json" -d "{\"phone\":\"18011119108\"}"

# 登录或自动注册
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"phone\":\"18011119108\",\"code\":\"123456\",\"type\":1}"
```

返回的 token 放到 `Authorization: Bearer <token>` 请求头，即可发布和查询笔记。

## 教程映射

`pagemarkdown-markdown-batch-20260811-194454/教程顺序.txt` 是完整教程顺序；本工程按同一顺序保留了各阶段的服务包、DTO、DO、Mapper、消费者和配置类。部分依赖（RocketMQ、ES、Canal、MinIO、Cassandra 等）需要在 IDEA/Maven 可以访问中央仓库时下载。
