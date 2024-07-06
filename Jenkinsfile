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
                script {
                    def buildNumber = env.BUILD_NUMBER
                    def dockerImageTag = "mirfanduri/demo-ci-cd-jenkins:${buildNumber}"

                    sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"
                    sh "docker build -t ${dockerImageTag} ."
                    sh "docker push ${dockerImageTag}"
                    sh "docker run -d -p 8081:8080 ${dockerImageTag}"
                }
            }
        }
    }

    post {
        always {
            // Archive the artifacts
            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
        }
        success {
            script {
                def slackMessageBlocks = '''[
                    {
                        "type": "section",
                        "text": {
                            "type": "mrkdwn",
                            "text": "Hello, Assistant to the Regional Manager Dwight! *Michael Scott* wants to know where you'd like to take the Paper Company investors to dinner tonight.\\n\\n *Please select a restaurant:*"
                        }
                    },
                    {
                        "type": "divider"
                    },
                    {
                        "type": "section",
                        "text": {
                            "type": "mrkdwn",
                            "text": "*Farmhouse Thai Cuisine*\\n:star::star::star::star: 1528 reviews\\nThey do have some vegan options, like the roti and curry, plus they have a ton of salad stuff and noodles can be ordered without meat!! They have something for everyone here"
                        },
                        "accessory": {
                            "type": "image",
                            "image_url": "https://s3-media3.fl.yelpcdn.com/bphoto/c7ed05m9lC2EmA3Aruue7A/o.jpg",
                            "alt_text": "alt text for image"
                        }
                    }
                ]'''
                slackSend(channel: SLACK_CHANNEL, color: 'good', message: "Pipeline succeeded: ${env.JOB_NAME} [${env.BUILD_NUMBER}] (${env.BUILD_URL})", blocks: slackMessageBlocks)
            }
        }
        failure {
            script {
                def slackMessageBlocks = '''[
                    {
                        "type": "section",
                        "text": {
                            "type": "mrkdwn",
                            "text": "Pipeline failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}] (${env.BUILD_URL})"
                        }
                    }
                ]'''
                slackSend(channel: SLACK_CHANNEL, color: 'danger', message: "Pipeline failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}] (${env.BUILD_URL})", blocks: slackMessageBlocks)
            }
        }
    }
}
