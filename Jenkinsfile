pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        SLACK_CHANNEL = '#demo-project'
    }

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                git url: 'https://github.com/sidulanaksekolahan/demo-ci-cd-jenkins.git', branch: 'master'
            }
        }
        stage('Build') {
            steps {
                // Set execute permissions for the mvnw script
                sh 'chmod +x ./mvnw'
                // Build the Spring Boot application
                sh './mvnw clean package'
            }
        }
        stage('Test') {
            steps {
                // Run tests
                sh './mvnw test'
            }
        }
        stage('Stop Existing Container') {
                    steps {
                        script {
                            // Stop and remove any existing container running on port 8081
                            sh '''
                            existing_container=$(docker ps -q --filter "publish=8081")
                            if [ -n "$existing_container" ]; then
                                docker stop $existing_container
                                docker rm $existing_container
                            fi
                            '''
                        }
                    }
                }
        stage('Deploy') {
            steps {
                def buildNumber = env.BUILD_NUMBER
                def dockerImageTag = "mirfanduri/demo-ci-cd-jenkins:${buildNumber}"

                sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"
                sh "docker build -t ${dockerImageTag} ."
                sh "docker push ${dockerImageTag}"
                sh "docker run -d -p 8081:8080 ${dockerImageTag}"
            }
        }
    }

    post {
        always {
            // Archive the artifacts
            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
        }
        success {
            echo 'Pipeline succeeded!'
            slackSend (channel: SLACK_CHANNEL, color: 'good', message: "Pipeline succeeded: ${env.JOB_NAME} [${env.BUILD_NUMBER}] (${env.BUILD_URL})")
        }
        failure {
            echo 'Pipeline failed!'
            slackSend (channel: SLACK_CHANNEL, color: 'danger', message: "Pipeline failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}] (${env.BUILD_URL})")
        }
    }
}
