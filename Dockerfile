# 使用体积较小的 JDK 17 运行时镜像
FROM eclipse-temurin:17-jre-alpine

# 创建工作目录
WORKDIR /app

# 只拷贝 build/libs 下的 jar 包
COPY build/libs/*.jar app.jar

# 暴露8080端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"] 