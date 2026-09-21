# xiaolanshu

基于教程逐步整理的全栈微服务练习项目，项目名为 `xiaolanshu`，作者署名为 `chisa`。

## 目录结构

- `src/main/java/com/quanxiaoha/framework`：通用响应、异常、上下文、操作日志与工具类。
- `src/main/java/com/quanxiaoha/xiaolanshu/*`：认证、网关、对象存储、用户、KV、笔记、关系、计数、搜索、评论、数据对齐等教程服务代码。
- `src/main/resources/db/schema.sql`：教程中整理出的 MySQL 表结构。
- `src/main/resources/mapper`：MyBatis Mapper XML。
- `src/main/java/com/quanxiaoha/xiaolanshu/app`：不依赖外部基础设施的轻量本地联调入口。

## 环境

- JDK 17
- Maven 3.9+
- MySQL 8.x：连接信息通过 `DB_URL`、`DB_USERNAME`、`DB_PASSWORD` 配置
- Redis：连接信息通过 `REDIS_HOST`、`REDIS_PORT`、`REDIS_PASSWORD` 配置
- Nacos：`127.0.0.1:8848`
- RocketMQ NameServer：`127.0.0.1:9876`
- Cassandra：`127.0.0.1:9042`，本地数据中心 `datacenter1`
- Elasticsearch：`127.0.0.1:9200`，版本 `7.3.0`，IK 插件 `7.3.0`
- MinIO：API `9000`，控制台 `9001`

Maven 本地仓库、临时目录和日志建议放在 `D:\Environment`，避免占用 C 盘。例如：

```powershell
$env:TEMP='D:\Environment\temp'
$env:TMP='D:\Environment\tmp'
$env:MAVEN_OPTS='-Djava.io.tmpdir=D:\Environment\maven-tmp'
& 'D:\Environment\apache-maven-3.9.16\bin\mvn.cmd' `
  -s 'D:\Environment\apache-maven-3.9.16\conf\settings.xml' `
  '-Dmaven.repo.local=D:\Environment\apache-maven-3.9.16\repo' test
```

## 常用开发工具

- **Apipost**：Gateway 接口地址为 `http://localhost:8000`。
- **Another Redis Desktop Manager**：使用本地 `.env` 中的 Redis 连接参数，数据库选择 `0`。
- **Navicat for MySQL**：使用本地 `.env` 中的 MySQL 连接参数。

先复制 `.env.example` 为本机 `.env`，只在本机填写数据库、Redis、短信和对象存储配置。`.env` 已被 Git 忽略，禁止提交真实凭证。

Redis 和 RocketMQ 可以使用项目根目录的 `docker-compose.yml` 启动：

```powershell
docker compose --env-file .env up -d redis rocketmq-namesrv rocketmq-broker rocketmq-dashboard
```

如果 Docker Desktop 尚未启动，请先启动 Docker Desktop。Compose 默认把 Redis 数据、RocketMQ 日志和消息存储写入 `D:/Environment/xiaolanshu/docker`，也可以通过 `XIAOLANSHU_DATA_DIR` 修改。RocketMQ Dashboard 地址为 `http://localhost:18081`，避免占用 OSS 的 `8081` 端口。不要启动 RabbitMQ。

## 微服务启动

在 IDEA 中以 Maven 项目导入根目录，使用 JDK 17。每个服务选择对应启动类，并在 Run Configuration 的 `Active profiles` 填写 profile：

| 服务 | 启动类 | Profile | 端口 |
| --- | --- | --- | ---: |
| Gateway | `xiaolanshuGatewayApplication` | `gateway` | 8000 |
| Auth | `xiaolanshuAuthApplication` | `auth` | 8080 |
| OSS | `xiaolanshuOssBizApplication` | `oss` | 8081 |
| User | `xiaolanshuUserBizApplication` | `user` | 8082 |
| KV | `xiaolanshuKVBizApplication` | `kv` | 8084 |
| Distributed ID | `xiaolanshuDistributedIdGeneratorBizApplication` | `distributed-id-generator` | 8085 |
| Note | `xiaolanshuNoteBizApplication` | `note` | 8086 |
| User relation | `xiaolanshuUserRelationBizApplication` | `user-relation` | 8087 |
| Count | `xiaolanshuCountBizApplication` | `count` | 8090 |
| Data align | `xiaolanshuDataAlignApplication` | `data-align` | 8091 |
| Search | `xiaolanshuSearchApplication` | `search` | 8092 |
| Comment | `xiaolanshuCommentBizApplication` | `comment` | 8093 |

推荐启动顺序：MySQL、Redis、Nacos、RocketMQ、Cassandra、Elasticsearch、MinIO，然后启动 Auth/User/OSS/KV/Distributed ID，再启动 Note/Relation/Count/Search/Comment/Data align，最后启动 Gateway。

Gateway 地址为 `http://localhost:8000`，例如：

```text
POST http://localhost:8000/auth/verification/code/send
PUT  http://localhost:8000/user/user/update
```

登录后把返回的 token 放入 `Authorization: Bearer <token>` 请求头，再调用需要鉴权的接口。

根目录的 `com.quanxiaoha.xiaolanshu.XiaolanshuApplication` 是不依赖外部基础设施的轻量联调入口，只用于验证 `app` 包主链路，不代表 12 个微服务全部启动。

## 可选教程组件

XXL-JOB、Canal、Leaf Snowflake 的 ZooKeeper 模式、Kibana、Elasticsearch Head、Logstash、Sentinel Dashboard 按 `pagemarkdown-markdown-batch-20260811-194454/教程顺序.txt` 对应小节安装。当前应用默认关闭 Canal、XXL-JOB 和 Leaf ZooKeeper 模式，需要时通过 `.env` 显式开启：

- `XXL_JOB_ENABLED=true`，并配置 `XXL_JOB_ADMIN_ADDRESSES` 和执行器参数。
- `CANAL_ENABLED=true`，并配置 Canal destination、账号和订阅规则。
- `LEAF_SNOWFLAKE_ZOOKEEPER_ENABLED=true`，并确保 ZooKeeper 在 `2181` 监听。

教程中的 MQ 失败消息落库补偿属于后续扩展，`SendMqRetryHelper` 保留教程原有的预留点，没有虚构数据库表或定时扫描逻辑。

## 教程映射

`pagemarkdown-markdown-batch-20260811-194454/教程顺序.txt` 是完整教程顺序；本工程按同一顺序保留各阶段的服务包、DTO、DO、Mapper、消费者和配置类。完整参考源码保存在被 Git 忽略的 `xiaohashu2` 目录中，便于对照，不会推送到远端。