pipeline {
	agent any

	environment {
		DOCKER_TAG = "1.0.${BUILD_NUMBER}"
		SSH_USER = 'wo4ko'
		REMOTE_HOST = '84.201.153.184'
	}

	stages {
		stage('Set env variables') {
			steps {
				script {
					withCredentials([
						usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'DOCKER_CREDS_PSW', usernameVariable: 'DOCKER_USERNAME'),
					]) {
						env.BACKEND_IMAGE = "${DOCKER_USERNAME}/spring:${DOCKER_TAG}"
						env.FRONTEND_IMAGE = "${DOCKER_USERNAME}/nginx:${DOCKER_TAG}"
					}
				}
			}
		}
		stage('Checkout') {
			steps {
				git branch: 'test', url: 'https://github.com/vsnitko/oauth2.git'
			}
		}
		stage('Build Backend (Spring Boot)') {
			steps {
				dir('spring-oauth2') {
					script {
						sh 'mvn clean package -B'
					}
				}
			}
		}
		stage('Build Frontend (React)') {
			steps {
				dir('react-oauth2') {
					script {
						sh 'npm install'
						sh 'CI=false npm run build'
					}
				}
			}
		}
		stage('Build docker-compose') {
			steps {
				script {
					sh "DOCKERFILE='Dockerfile' docker-compose build"
				}
			}
		}
		stage('Push docker-compose to DockerHub') {
			steps {
				script {
					withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'DOCKER_CREDS_PSW', usernameVariable: 'DOCKER_CREDS_USR')]) {
						sh 'echo $DOCKER_CREDS_PSW | docker login --username $DOCKER_CREDS_USR --password-stdin'
						sh "docker-compose push"
					}
				}
			}
		}
		stage('Pull docker-compose and Restart Containers in Cloud') {
			steps {
				script {
					sshagent(['ssh']) {
						withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'DOCKER_CREDS_PSW', usernameVariable: 'DOCKER_CREDS_USR')]){
							sh "scp docker-compose.yml ${SSH_USER}@${REMOTE_HOST}:/opt/oauth2/"

							def variables = [
								"BACKEND_IMAGE=${BACKEND_IMAGE}",
								"FRONTEND_IMAGE=${FRONTEND_IMAGE}"
							]
							for (variable in variables) {
								def (key, value) = variable.split('=')
								sh """ssh -o StrictHostKeyChecking=no ${SSH_USER}@${REMOTE_HOST} '
								if grep -q "^${key}=" /etc/environment; then
    							    sudo sed -i "s/^${key}=.*/${variable}/" /etc/environment
    							else
    							    echo "${variable}" | sudo tee -a /etc/environment
    							fi
								'"""
							}

							sh """ssh ${SSH_USER}@${REMOTE_HOST} '
							source /etc/environment
							echo ${DOCKER_CREDS_PSW} | docker login --username ${DOCKER_CREDS_USR} --password-stdin
							cd /opt/oauth2
							mkdir -p spring-oauth2 react-oauth2
							docker-compose pull
							docker-compose up -d --no-build
							'"""
						}
					}
				}
			}
		}
	}
}
