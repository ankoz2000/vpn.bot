FROM gradle:8.6-jdk17
WORKDIR /app
COPY . .
EXPOSE 8080
RUN export $(cat ./config/app.config | xargs)
RUN gradle build --no-daemon 
ENTRYPOINT ["gradle", "bootRun"]
