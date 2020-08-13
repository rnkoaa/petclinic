FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR application
ARG APP_VERSION="0.0.1-SNAPSHOT"
ENV APP_VERSION=$APP_VERSION
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract
 
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /application

ENV PORT=8080

COPY entrypoint.sh /application/bin/
RUN chmod +x /application/bin/entrypoint.sh

# expose data for sqlite data
VOLUME /application/data
VOLUME /application/config
VOLUME /application/secret

ENV GOOGLE_APPLICATION_CREDENTIALS=/application/secret/token.json

COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader ./
# COPY --from=builder application/internal-dependencies ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
ENTRYPOINT ["/application/bin/entrypoint.sh"]

EXPOSE $PORT