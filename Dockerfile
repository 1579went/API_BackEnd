# Docker 镜像构建(远程拉取代码后打包)

#FROM maven:3.8.4-openjdk-17-slim as builder

## Copy local code to the container image.
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#
## Build a release artifact.
#RUN mvn package -DskipTests

## Run the web service on container startup.
#CMD ["java","-jar","/app/target/API_BackEnd-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]

# Docker 镜像构建(本地打包上传)
FROM java:17
# 作者
MAINTAINER went
# 添加jar到镜像并命名为user.jar
ADD API_BackEnd-0.0.1-SNAPSHOT.jar API_BackEnd-0.0.1.jar
# 镜像启动后暴露的端口
EXPOSE 8101
# Run the web service on container startup.
CMD ["java","-jar","API_BackEnd-0.0.1.jar","--spring.profiles.active=prod"]
