pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare') {
            steps {
                sh 'chmod +x gradlew'
            }
        }

        stage('Run tests (ignore failures)') {
            steps {
                sh './gradlew clean test || true'
            }
        }
    }
}