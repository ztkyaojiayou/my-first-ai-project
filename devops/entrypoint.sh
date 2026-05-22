#!/bin/sh
# JVM 优化参数
JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"

# 应用配置参数
APP_OPTS="--server.port=8080 --spring.profiles.active=prod"

# 启动 Java 应用
exec java $JAVA_OPTS $APP_OPTS -jar app.jar
