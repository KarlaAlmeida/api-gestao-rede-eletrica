pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'karlaalmeida/karlaapi'
        IMAGE_TAG = 'latest'
        K8S_NAMESPACE = 'karla-devops'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Maven') {
            steps {
                bat 'mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %DOCKER_IMAGE%:%IMAGE_TAG% ."
            }
        }

        stage('Docker Hub Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-cred',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat 'echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                bat "docker push %DOCKER_IMAGE%:%IMAGE_TAG%"
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat "kubectl apply -f k8s/namespace.yaml"
                bat "kubectl apply -f k8s/secret.yaml -n %K8S_NAMESPACE%"
                bat "kubectl apply -f k8s/postgres.yaml -n %K8S_NAMESPACE%"
                bat "kubectl apply -f k8s/app.yaml -n %K8S_NAMESPACE%"
                bat "kubectl apply -f monitoring/prometheus-configmap.yaml -n %K8S_NAMESPACE%"
                bat "kubectl apply -f monitoring/prometheus-pvc.yaml -n %K8S_NAMESPACE%"
                bat "kubectl apply -f monitoring/prometheus.yaml -n %K8S_NAMESPACE%"
                bat "kubectl apply -f k8s/grafana.yaml -n %K8S_NAMESPACE%"
            }
        }

        stage('Restart Application Rollout') {
            steps {
                bat "kubectl rollout restart deployment/karlaapi -n %K8S_NAMESPACE%"
            }
        }

        stage('Check Rollout Status') {
            steps {
                bat "kubectl rollout status deployment/postgres -n %K8S_NAMESPACE%"
                bat "kubectl rollout status deployment/karlaapi -n %K8S_NAMESPACE%"
                bat "kubectl rollout status deployment/prometheus -n %K8S_NAMESPACE%"
                bat "kubectl rollout status deployment/grafana -n %K8S_NAMESPACE%"
            }
        }

        stage('Check Cluster Resources') {
            steps {
                bat "kubectl get all -n %K8S_NAMESPACE%"
                bat "kubectl get pvc -n %K8S_NAMESPACE%"
            }
        }
    }

    post {
        always {
            bat 'docker logout'
        }
        success {
            echo 'Pipeline executado com sucesso.'
        }
        failure {
            echo 'Pipeline falhou. Verifique os logs de cada stage.'
        }
    }
}