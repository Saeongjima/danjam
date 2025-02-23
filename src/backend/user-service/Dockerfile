# 베이스 이미지: OpenJDK 17
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사 (env.properties 제외된 빌드 파일)
COPY build/libs/*.jar app.jar

# 컨테이너 실행 시 환경 변수 적용 가능하도록 설정
ENTRYPOINT ["java", "-jar", "app.jar"]
