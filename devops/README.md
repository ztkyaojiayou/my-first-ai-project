# 用户管理系统 - 运维部署说明

## 目录内容

| 文件/目录 | 说明 |
|-----------|------|
| `user-demo-1.0.0.jar` | 预编译的 JAR 包（已构建好，可直接运行） |
| `Dockerfile` | 多阶段构建镜像（需要 Maven 环境） |
| `Dockerfile.simple` | 简化版 Dockerfile（使用预编译 JAR） |
| `docker-compose.yml` | 容器编排文件（后端 + nginx） |
| `build.sh` | 打包脚本 |
| `deploy.sh` | 部署脚本 |
| `entrypoint.sh` | 容器启动脚本 |
| `nginx/` | nginx 配置（前端托管 + 反向代理） |

---

## 推荐部署方式（无需 Docker）

由于当前 Docker 镜像源存在网络问题，**推荐直接使用预编译的 JAR 包**：

```bash
cd devops
java -jar user-demo-1.0.0.jar
```

这样就可以启动后端服务，监听 8080 端口。

---

## 方式一：直接运行 JAR（推荐）

```bash
cd devops

# 启动后端服务
java -jar user-demo-1.0.0.jar

# 使用指定 JVM 参数
java -Xms256m -Xmx512m -jar user-demo-1.0.0.jar
```

---

## 方式二：Docker 构建（待网络恢复后）

如果网络正常，可以按以下步骤构建 Docker 镜像：

### 方案 A：使用简化版 Dockerfile（推荐）
```bash
cd devops
docker build -f Dockerfile.simple -t userdemo:1.0.0 .
docker tag userdemo:1.0.0 userdemo:latest
```

### 方案 B：使用多阶段构建
```bash
cd devops
docker build -t userdemo:1.0.0 .
```

---

## 方式三：Docker Compose 部署（待网络恢复）

```bash
cd devops
docker-compose up -d
```

或者使用部署脚本：
```bash
cd devops
./deploy.sh start
```

---

## Docker 镜像源问题说明

目前阿里云镜像源对基础镜像（openjdk:8、eclipse-temurin:8）返回 `403 Forbidden`。

解决建议：
1. **直接使用 JAR 包运行**（最推荐，无需 Docker）
2. 临时禁用 Docker 镜像源，直接从 Docker Hub 官方源拉取
3. 配置其他国内镜像源（如中科大镜像、网易镜像等）

---

## 健康检查

后端服务启动后：
```bash
curl http://localhost:8080/api/health/check
```

---

## 默认测试账号

| 用户名 | 密码 |
|--------|------|
| admin | 123456 |
| test | 123456 |
