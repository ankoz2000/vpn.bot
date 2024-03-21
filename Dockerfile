FROM maven:3.6.3-jdk-11-slim
WORKDIR /app
COPY . .
EXPOSE 8080
RUN gradle install --no-transfer-progress -DskipTests=true
ENTRYPOINT ["gradle", "bootRun"]