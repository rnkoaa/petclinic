FROM adoptopenjdk:11-jre-hotspot as builder

WORKDIR application

# download and executable which can be used to download files.
RUN curl -o gcp-storage-download-linux-amd64-0.0.2.tar.gz \
    https://storage.googleapis.com/gcp-cloud-storage-download/gcp-storage-download-linux-amd64-0.0.2.tar.gz && \
    tar -xvf gcp-storage-download-linux-amd64-0.0.2.tar.gz && \
    mv gcp-storage-download-linux-amd64-0.0.2 gcp-storage-download && \
    chmod +x ./gcp-storage-download && \
    rm gcp-storage-download-linux-amd64-0.0.2.tar.gz

ARG APP_VERSION="0.0.1-SNAPSHOT"
ENV APP_VERSION=$APP_VERSION
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract
 
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /application

ENV PORT=8080
ENV CASSANDRA_BUNDLE_BUCKET=bundle-bucket
ENV CASSANDRA_BUNDLE_FILE_NAME=bundle-name

COPY entrypoint.sh /application/bin/
RUN chmod +x /application/bin/entrypoint.sh

# expose data for sqlite data
VOLUME /application/data
VOLUME /application/config

COPY --from=builder /application/gcp-storage-download /application/bin
COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader ./
# COPY --from=builder application/internal-dependencies ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
ENTRYPOINT ["/application/bin/entrypoint.sh"]

EXPOSE $PORT
