FROM gradle:8.6-jdk17
WORKDIR /app
COPY . .
EXPOSE 8080
RUN ls -la
RUN export $(cat ./config/app.config | xargs)
ENV USER_BOT_TOKEN = ${{ secrets.USER_BOT_TOKEN }}
ENV paymentToken = ${{ secrets.paymentToken }}
ENV shopId = ${{ secrets.shopId }}
ENV testCard = ${{ secrets.testCard }}
ENV shopArticleId = ${{ secrets.shopArticleId }}
ENV OUTLINE = ${{ secrets.OUTLINE }}
RUN gradle build --no-daemon 
ENTRYPOINT ["gradle", "bootRun"]
