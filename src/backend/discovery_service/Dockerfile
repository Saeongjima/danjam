FROM openjdk:17-jdk-slim
WORKDIR /app

# JAR 파일과 환경설정 파일을 한 번에 복사
COPY build/libs/*.jar app.jar
COPY src/main/java/resources/properties/env.properties ./env.properties

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

