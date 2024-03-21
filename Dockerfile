FROM gradle:8.6-jdk17
WORKDIR /app
COPY . .
EXPOSE 8080
RUN gradle install --no-transfer-progress -DskipTests=true
ENTRYPOINT ["gradle", "bootRun"]