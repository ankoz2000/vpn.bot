FROM gradle:8.6-jdk17
WORKDIR /app
COPY . .
EXPOSE 8080
RUN ls -la
RUN export $(cat ./config/app.config | xargs)
ENV USER_BOT_TOKEN = $USER_BOT_TOKEN
ENV paymentToken = $paymentToken
ENV shopId = $shopId
ENV testCard = $testCard
ENV shopArticleId = $shopArticleId
ENV OUTLINE = $OUTLINE
RUN gradle build --no-daemon 
ENTRYPOINT ["gradle", "bootRun"]
