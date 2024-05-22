FROM bellsoft/liberica-openjdk-alpine:21
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./build/libs/flash-cards-api-0.0.1-SNAPSHOT.jar ./flash-cards-api-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar flash-cards-api-0.0.1-SNAPSHOT.jar"]
