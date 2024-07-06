pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                git url: 'https://github.com/sidulanaksekolahan/demo-ci-cd-jenkins.git', branch: 'master'
            }
        }
        stage('Build') {
            steps {
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
        stage('Deploy') {
            steps {
                // Deploy the application
                // Assuming you are using Docker for deployment
                sh 'docker build -t mirfanduri/demo-ci-cd-jenkins:latest .'
                sh 'docker push mirfanduri/demo-ci-cd-jenkins:latest
                sh 'docker run -d -p 8080:8080 mirfanduri/demo-ci-cd-jenkins:latest'
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
