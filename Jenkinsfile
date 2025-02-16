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
                    withCredentials([file(credentialsId: 'danjam-discovery-service-env', variable: 'ENV_PROPERTIES_FILE')]) {
                        echo "Copying env.properties from Secret file"

                        // env.properties를 빌드 시 필요한 위치로 복사
                       withEnv(["SECRET_FILE=${ENV_PROPERTIES_FILE}"]) {
                           sh '''
                           cp $SECRET_FILE src/backend/discovery_service/src/main/resources/properties/env.properties
                           '''
                       }
                    }
                }
            }
        }
        // 빌드 단계
        stage('Build Backend') {
            steps {
                dir('src/backend/discovery_service') { // backend/discovery-service 디렉토리로 이동
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
                dir('src/backend/discovery_service') {
                    script {
                        echo "Running Backend Tests..."
                        sh './gradlew test' // Gradle 테스트
                    }
                }
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'agong1-docker', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        dir('src/backend/discovery_service') {
                            sh '''
                            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                            docker build -t agong1/danjam-discovery-service:latest .
                            docker push agong1/danjam-discovery-service:latest
                            '''
                        }
                    }
                }
            }
        }

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
                                        /usr/bin/docker stop danjam-discovery-service || true
                                        /usr/bin/docker rm danjam-discovery-service || true

                                        echo "Pulling latest Docker image..."
                                        /usr/bin/docker pull agong1/danjam-discovery-service:latest

                                        echo "Running new container..."
                                        /usr/bin/docker run -d --name danjam-discovery-service --network npm_default -p 8761:8761 agong1/danjam-discovery-service:latest
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
