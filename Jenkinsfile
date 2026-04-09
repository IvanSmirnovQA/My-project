pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Show files') {
            steps {
                sh 'pwd'
                sh 'ls -la'
            }
        }

        stage('Make gradlew executable') {
            steps {
                sh 'chmod +x gradlew'
            }
        }

        stage('Run tests') {
            steps {
                sh './gradlew clean test'
            }
        }
    }
}