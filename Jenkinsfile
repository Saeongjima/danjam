pipeline {
    agent any
    environment {
        JAVA_HOME = '/opt/java/openjdk'
        GRADLE_OPTS = '-Dorg.gradle.daemon=true -Dorg.gradle.parallel=true'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }
    options {
        skipStagesAfterUnstable() // 실패한 단계 이후 실행 중지
        timeout(time: 30, unit: 'MINUTES') // 파이프라인 전체 타임아웃
    }
    stages {
        stage('Checkout') {
            steps {
                // Git에서 코드 체크아웃
                checkout scm
            }
        }
        // 환경변수 파일 생성 단계
        stage('Generate env.properties') {
            steps {
                script {
                    withCredentials([file(credentialsId: 'danjam-api-gateway-service-env', variable: 'ENV_PROPERTIES_FILE')]) {
                        echo "Copying env.properties from Secret file"

                        // env.properties를 빌드 시 필요한 위치로 복사
                        withEnv(["SECRET_FILE=${ENV_PROPERTIES_FILE}"]) {
                            sh '''
                            # 필요한 디렉토리 생성
                            mkdir -p src/backend/api_gateway_service/src/main/resources/properties/
                            # env.properties 파일 복사
                            cp $SECRET_FILE src/backend/api_gateway_service/src/main/resources/properties/env.properties
                            '''
                        }
                    }
                }
            }
        }
        // 빌드 단계
        stage('Build Backend') {
            steps {
                dir('src/backend/api_gateway_service') { // backend/api_gateway_service 디렉토리로 이동
                    script {
                        echo "Building Backend..."
                        sh '''
                        chmod +x ./gradlew
                        ./gradlew clean build --parallel
                        '''
                    }
                }
            }
        }
        // 테스트 단계
        stage('Test Backend') {
            steps {
                dir('src/backend/api_gateway_service') {
                    script {
                        echo "Running Backend Tests..."
                        sh './gradlew test' // Gradle 테스트
                    }
                }
            }
        }
        // Docker 이미지 빌드 및 푸시 단계
        stage('Build and Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'agong1-docker', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        dir('src/backend/api_gateway_service') {
                            sh '''
                            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                            docker build -t agong1/danjam-api-gateway-service:latest .
                            docker push agong1/danjam-api-gateway-service:latest
                            '''
                        }
                    }
                }
            }
        }
        // 서버에 배포 단계
        stage('Deploy to Server') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'kangmin-oracle-orm',
                            transfers: [
                                sshTransfer(
                                    execCommand: '''
                                        echo "Stopping existing container..."
                                        /usr/bin/docker stop danjam-api-gateway-service || true
                                        /usr/bin/docker rm danjam-api-gateway-service || true

                                        echo "Pulling latest Docker image..."
                                        /usr/bin/docker pull agong1/danjam-api-gateway-service:latest

                                        echo "Running new container..."
                                        /usr/bin/docker run -d --name danjam-api-gateway-service --network npm_default -p 8600:8600 agong1/danjam-api-gateway-service:latest
                                    ''',
                                    execTimeout: 120000
                                )
                            ],
                            verbose: false
                        )
                    ]
                )
            }
        }
    }
    post {
        success {
            echo 'Pipeline completed successfully! 🎉'
        }
        failure {
            echo 'Pipeline failed! ❌'
        }
    }
}
