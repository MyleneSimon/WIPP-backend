FROM eclipse-temurin:8-jdk-alpine

MAINTAINER National Institute of Standards and Technology

EXPOSE 8080

ARG BACKEND_NAME="wipp-backend-application"
ARG EXEC_DIR="/opt/wipp"
ARG DATA_DIR="/data/WIPP-plugins"
ARG ARGO_VERSION="v2.3.0"
ARG APM_VERSION="1.9.0"

COPY deploy/docker/VERSION /VERSION

# Create exec and data folders
RUN mkdir -p \
  ${EXEC_DIR}/config \
  ${DATA_DIR}

# Download Elastic APM Java Agent
RUN wget https://repo1.maven.org/maven2/co/elastic/apm/elastic-apm-agent/${APM_VERSION}/elastic-apm-agent-${APM_VERSION}.jar -O ${EXEC_DIR}/elastic-apm-agent.jar

# Install Argo CLI executable
RUN wget https://github.com/argoproj/argo-workflows/releases/download/${ARGO_VERSION}/argo-linux-amd64 && \
    chmod +x argo-linux-amd64 && \
    mv argo-linux-amd64 /usr/local/bin/argo

# Install Python and dependencies
RUN apk update
RUN apk add --no-cache --update python3 python3-dev py3-pip gcc g++ make linux-headers
RUN pip3 install --break-system-packages --upgrade pip
RUN pip3 install --break-system-packages cvat-sdk pymongo regex opencv-python-headless
RUN pip3 install --break-system-packages pycocotools potracer

# Copy WIPP backend application exec WAR
COPY ${BACKEND_NAME}/target/${BACKEND_NAME}-*-exec.war ${EXEC_DIR}/wipp-backend.war

# Copy properties and entrypoint script
COPY deploy/docker/application.properties ${EXEC_DIR}/config
COPY deploy/docker/entrypoint.sh /usr/local/bin

# Set working directory
WORKDIR ${EXEC_DIR}

# Entrypoint
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
