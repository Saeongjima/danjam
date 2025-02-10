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

        // 서버에 jar파일 복사 및 배포 단계
        stage('Deploy Backend') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'kangmin-oracle-orm',
                            transfers: [
                                sshTransfer(
                                    sourceFiles: 'src/backend/discovery_service/build/libs/*-SNAPSHOT.jar',
                                    excludes: '*-plain.jar',
                                    remoteDirectory: '/discovery-service',
                                    removePrefix: 'src/backend/discovery_service/build/libs'
                                )
                            ]
                        )
                    ]
                )
            }
        }
        // jar파일 실행
        stage('Execute Remote Commands') {
            steps {
                sshagent(['kangmin-oracle-orm']) {
                    sh '''
                    echo "Adding execution permissions to JAR files..."
                    chmod +x /discovery-service/discovery_service-*.jar

                    echo "Stopping any running instance of discovery-service..."
                    pkill -f 'discovery-service' || echo 'No running service found.'

                    echo "Finding the latest JAR file..."
                    latest_jar=$(ls -t /discovery-service/discovery_service-*.jar | head -n 1)
                    echo "Starting $latest_jar"
                    nohup java -jar $latest_jar > /discovery-service/app.log 2>&1 &
                    '''
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
