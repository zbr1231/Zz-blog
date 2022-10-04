# 基础镜像
FROM java:8
# 作者信息
LABEL maintainer="xxxx.com"
# 将 maven 目录的 jar 包拷贝到 docker 中，并命名为 for_docker.jar
ADD maven/sangeng-blog-1.0-SNAPSHOT.jar blog.jar
# 容器启动执行命令
ENTRYPOINT ["java", "-jar", "blog.jar"]