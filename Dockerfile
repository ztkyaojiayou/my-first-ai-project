# 构建阶段：使用 Maven 打包项目
FROM maven:3.9-eclipse-temurin-8 AS builder

# 设置工作目录
WORKDIR /app

# 先复制 pom.xml，获取依赖（利用 Docker 缓存层）
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 复制源代码并打包
COPY src ./src
RUN mvn package -DskipTests -B

# 运行阶段：使用精简 JRE 镜像
FROM eclipse-temurin:8-jre-alpine

# 创建非 root 用户
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=builder /app/target/*.jar app.jar

# 复制 entrypoint 脚本
COPY --chmod=+x entrypoint.sh .

# 切换到非 root 用户
USER appuser

# 暴露端口
EXPOSE 8080

# 使用 exec 形式启动 Java 应用（确保信号正确传递）
ENTRYPOINT ["./entrypoint.sh"]
