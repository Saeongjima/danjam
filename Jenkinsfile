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
        // 빌드 단계
        stage('Build Backend') {
            steps {
                dir('src/backend/user-service') { // backend/해당 service 디렉토리로 이동
                    script {
                        echo "Building Backend..."
                        sh '''
                        chmod +x ./gradlew
                        ./gradlew clean build -x test --parallel  # 테스트 제외하고 빌드
                        '''
                    }
                }
            }
        }
        // 환경변수 파일 생성 단계
        stage('Generate env.properties') {
            steps {
                script {
                    withCredentials([file(credentialsId: 'danjam-user-service-env', variable: 'ENV_PROPERTIES_FILE')]) {
                        echo "Copying env.properties from Secret file"

                        // env.properties를 빌드 시 필요한 위치에 복사
                        withEnv(["SECRET_FILE=${ENV_PROPERTIES_FILE}"]) {
                            sh '''
                            # 필요한 디렉토리 생성
                            mkdir -p src/backend/user-service/src/main/resources/properties/
                            # env.properties 파일 복사
                            cp $SECRET_FILE src/backend/user-service/src/main/resources/properties/env.properties
                            '''
                        }
                    }
                }
            }
        }

        // 테스트 단계
        stage('Test Backend') {
            steps {
                dir('src/backend/user-service') {
                    script {
                        echo "Running Backend Tests..."
                        sh './gradlew test' // Gradle 테스트
                    }
                }
            }
        }
        // `env.properties` 삭제 단계 (Docker 이미지에 포함되지 않도록 함)
        stage('Remove env.properties') {
            steps {
                dir('src/backend/user-service') {
                    script {
                        echo "Removing env.properties before building Docker image..."
                        sh 'rm -f src/main/resources/properties/env.properties'
                    }
                }
            }
        }
        // Docker 이미지 빌드 및 푸시 단계
        stage('Docker Build and Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'agong1-docker', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        dir('src/backend/user-service') {
                            sh '''
                            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                            docker build -t agong1/danjam-user-service:latest .
                            docker push agong1/danjam-user-service:latest
                            '''
                        }
                    }
                }
            }
        }
        // 서버에 배포 단계 (env.properties 파일을 다시 복사하여 컨테이너 실행)
        stage('Deploy to Server') {
            steps {
                script {
                    withCredentials([file(credentialsId: 'danjam-user-service-env', variable: 'ENV_PROPERTIES_FILE')]) {
                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: 'kangmin-oracle-orm',
                                    transfers: [
//                                         // 1. Ensure /user-service/ directory exists and set permissions
//                                         sshTransfer(
//                                             execCommand: '''
//                                                 echo "Ensuring /user-service/ directory exists..."
//                                                 /usr/bin/mkdir -p /user-service/
//                                                 /usr/bin/chmod 777 /user-service/
//                                                 /usr/bin/ls -ld /user-service/
//                                             ''',
//                                             execTimeout: 60000
//                                         ),
//                                         // 2. Copy env.properties file
//                                         sshTransfer(
//                                             sourceFiles: "${ENV_PROPERTIES_FILE}",
//                                             remoteDirectory: "/user-service",
//                                             removePrefix: "${WORKSPACE}",
//                                             verbose: true
//                                         ),
//                                         // 3. Check if file exists and set correct permissions
//                                         sshTransfer(
//                                             execCommand: '''
//                                                 echo "Setting permissions for env.properties..."
//                                                 /usr/bin/chmod 644 /user-service/env.properties || echo "ERROR: Failed to set permissions!"
//                                                 /usr/bin/ls -l /user-service/env.properties
//                                             ''',
//                                             execTimeout: 600000
//                                         ),
                                        // 4. Docker 컨테이너 실행
                                        sshTransfer(
                                            execCommand: '''
                                                echo "Stopping existing container..."
                                                /usr/bin/docker stop danjam-user-service || true
                                                /usr/bin/docker rm danjam-user-service || true

                                                echo "Pulling latest Docker image..."
                                                /usr/bin/docker pull agong1/danjam-user-service:latest

                                                echo "Running new container with environment variables..."
                                                /usr/bin/docker run -d --name danjam-user-service \
                                                  --network npm_default \
                                                  -p 8601:8601 \
                                                  --env-file /user-service/env.properties \
                                                  agong1/danjam-user-service:latest
                                            ''',
                                            execTimeout: 120000
                                        )
                                    ],
                                    verbose: true
                                )
                            ]
                        )
                    }
                }
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
