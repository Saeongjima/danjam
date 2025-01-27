pipeline {
    agent any
    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-arm64'
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
        stage('Build Backend') {
            steps {
                dir('src/backend/user-service') { // backend/user-service 디렉토리로 이동
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
        stage('Deploy Backend') {
            steps {
                dir('src/backend/user-service') {
                    script {
                        echo "Deploying Backend..."
                        sh '''
                        docker build -t my-backend:latest .
                        docker tag my-backend:latest my-docker-registry/my-backend:latest
                        docker push my-docker-registry/my-backend:latest
                        '''
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
