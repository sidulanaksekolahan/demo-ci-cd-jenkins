pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
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
                // Login to Docker Hub
                sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"
                // Deploy the application
                // Assuming you are using Docker for deployment
                sh 'docker build -t mirfanduri/demo-ci-cd-jenkins:latest .'
                sh 'docker push mirfanduri/demo-ci-cd-jenkins:latest'
                sh 'docker run -d -p 8081:8080 mirfanduri/demo-ci-cd-jenkins:latest'
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
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
