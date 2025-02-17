FROM jenkins/jenkins:jdk21

USER root

#RUN chmod 660 /var/run/docker.sock && chown root:docker /var/run/docker.sock

RUN \
	apt-get update \
	&& apt-get install -y maven curl \
	&& curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
	&& apt-get install -y nodejs \
	&& npm install -g npm@latest \
	&& apt install -y apt-transport-https ca-certificates software-properties-common \
    && curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb\_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null \
    && apt update \
    && apt-cache policy docker-ce \
    && apt install -y docker-ce \
	&& curl -L "https://github.com/docker/compose/releases/download/v2.33.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose \
	&& chmod +x /usr/local/bin/docker-compose
RUN usermod -aG docker jenkins

USER jenkins

