pipeline {
    agent any
    environment {
        JAVA_HOME = '/opt/java/openjdk'
        GRADLE_OPTS = '-Dorg.gradle.daemon=true -Dorg.gradle.parallel=true'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        DOCKER_CREDENTIALS = credentials('docker-credentials')
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
                    // 동적으로 env.properties 생성
                    def envContent = "SERVER_PORT=8761"
                    def propertiesPath = 'src/backend/discovery_service/src/main/resources/properties/env.properties'
                    writeFile file: propertiesPath, text: envContent
                    echo "env.properties generated with SERVER_PORT=8761 at ${propertiesPath}"
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

        // // Docker 이미지 생성 및 배포 단계
        // stage('Deploy Backend') {
        //     steps {
        //         script {
        //             sh 'echo $DOCKER_CREDENTIALS_PSW | docker login -u $DOCKER_CREDENTIALS_USR --password-stdin' // Docker 레지스트리에 로그인
        //         }
        //         dir('src/backend/discovery_service') {
        //             script {
        //                 echo "Deploying Backend..."
        //                 sh '''
        //                 docker build -t my-backend:latest .
        //                 docker tag my-backend:latest my-docker-registry/my-backend:latest
        //                 docker push my-docker-registry/my-backend:latest
        //                 '''
        //             }
        //         }
        //     }
        // }
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
