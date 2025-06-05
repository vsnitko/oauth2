pipeline {
	agent any

	environment {
		DOCKER_TAG = "$BUILD_NUMBER"
        BACKEND_IMAGE = "$DOCKER_USERNAME/spring:$DOCKER_TAG"
        FRONTEND_IMAGE = "$DOCKER_USERNAME/react:$DOCKER_TAG"
        BACKEND_IMAGE_LATEST = "$DOCKER_USERNAME/spring:latest"
        FRONTEND_IMAGE_LATEST = "$DOCKER_USERNAME/react:latest"
	}

	stages {
		stage('Build Backend (Spring Boot)') {
			steps {
				dir('spring-oauth2') {
					script {
						sh 'mvn clean package -Dcheckstyle.skip=true -B'
						sh 'docker build -t $BACKEND_IMAGE -t $BACKEND_IMAGE_LATEST .'
					}
				}
			}
		}
        stage('Build Frontend (React)') {
        	steps {
        		dir('react-justchatting') {
        			script {
        				sh 'npm install'
        				sh 'npm run build'
        				sh 'docker build -t $FRONTEND_IMAGE -t $FRONTEND_IMAGE_LATEST .'
        			}
        		}
        	}
        }
        stage('Push Docker Images to DockerHub') {
        	steps {
        		dir('spring-oauth2') {
        			script {
        			    sh 'kubectl get pods'
        				sh 'echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin'
        				sh 'docker push $BACKEND_IMAGE'
        				sh 'docker push $FRONTEND_IMAGE'
        				sh 'docker push $BACKEND_IMAGE_LATEST'
                        sh 'docker push $FRONTEND_IMAGE_LATEST'
        			}
        		}
        	}
        }
        stage('Update Helm Chart') {
        	steps {
        		dir('k8s') {
        			script {
        				sh 'helm upgrade --install justchatting-prod ./helm-chart --set frontend.imageTag=$DOCKER_TAG --set backend.imageTag=$DOCKER_TAG'
        			}
        		}
        	}
        }
	}
}
