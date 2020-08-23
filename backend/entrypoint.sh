#!/bin/bash

set -ex

PATH=$PATH:$(pwd)

#JAVA_OPTS=""
AUTO_JAVA_OPTS=""
DEFAULT_JAVA_HEAP_PERCENT=80

# this ensures that credentials are available before continuing.
validateCredentials() {
  if [ -z "$CLOUD_ENVIRONMENT" ]; then
    echo "CLOUD_ENVIRONMENT is not set, this is required to continue"
    exit 1
  fi

  if [[ "$CLOUD_ENVIRONMENT" == "dev" && -z "$GOOGLE_APPLICATION_CREDENTIALS" ]]; then
    echo "GOOGLE_APPLICATION_CREDENTIALS is not specified"
    exit 1
  fi

  # check if the credentials file exists
  if [[ "$CLOUD_ENVIRONMENT" == "dev" && ! -f "$GOOGLE_APPLICATION_CREDENTIALS" ]]; then
    echo "$GOOGLE_APPLICATION_CREDENTIALS file does not exist for cloud environment $CLOUD_ENVIRONMENT, cannot continue."
    exit 1
  fi

  echo "$GOOGLE_APPLICATION_CREDENTIALS has been found."
}

# call the validateCredentials before proceeding
validateCredentials

# not disable_auto_heap
if [ -z "$DISABLE_JAVA_AUTO_HEAP" ]; then

  # If CONTAINER_MEM_MB is set, the heap is set to a percent of that
  # value equal to JAVA_HEAP_PERCENT; otherwise it uses the default
  # memory settings.
  if [ ! -z "$CONTAINER_MEM_MB" ]; then

    if [ -z "$JAVA_HEAP_PERCENT" ]; then
      JAVA_HEAP_PERCENT=$DEFAULT_JAVA_HEAP_PERCENT
    fi

    MEM_JAVA_MB=$(($CONTAINER_MEM_MB * $JAVA_HEAP_PERCENT / 100))

    echo "[Start Script] Configuring JVM max heap to ${JAVA_HEAP_PERCENT}% [${MEM_JAVA_MB}m]"
    AUTO_JAVA_OPTS="-Xmx${MEM_JAVA_MB}m"
  fi
fi

if [ ! -z "$AUTO_JAVA_OPTS" ]; then
  JAVA_OPTS="$AUTO_JAVA_OPTS"
fi

if [ -z "$JAVA_OPTS" ]; then
  JAVA_OPTS="-XX:MaxJavaStackTraceDepth=50"
else
  JAVA_OPTS="-XX:MaxJavaStackTraceDepth=50 $JAVA_OPTS"
fi

# if we are connecting to astra, we have to download the astra bundle before connecting
if [[ ! -z "$SPRING_PROFILES_ACTIVE" && "$SPRING_PROFILES_ACTIVE" == "astra" ]];
then
  # download the authentication bundle for connecting to datastax astra
  # if GOOGLE_APPLICATION_CREDENTIALS is defined
  if [[ ! -z "$CLOUD_ENVIRONMENT" && "$CLOUD_ENVIRONMENT" == "local" ]]; 
  then
    # if GOOGLE_APPLICATION_CREDENTIALS is not defined when running locally, this will not work
    if [ -z "$GOOGLE_APPLICATION_CREDENTIALS" ]; 
    then
      echo "$GOOGLE_APPLICATION_CREDENTIALS is required when not in gcp"
      exit 1
    fi
  fi

  if [ -z "$CASSANDRA_BUNDLE_BUCKET" ]; 
  then
    echo "CASSANDRA_BUNDLE_BUCKET env variable is required is required when not in gcp"
    exit 1
  fi


  if [ -z "$CASSANDRA_BUNDLE_FILE_NAME" ]; 
  then
    echo "CASSANDRA_BUNDLE_FILE_NAME env variable is required when not in gcp"
    exit 1
  fi

  # download cassandra bundle for astra
  /application/bin/gcp-storage-download -b "$CASSANDRA_BUNDLE_BUCKET" \
    -o "$CASSANDRA_BUNDLE_FILE_NAME" \
    -d "/application/$CASSANDRA_BUNDLE_FILE_NAME"
fi

# ensure port is defined
if [ -z "$PORT" ]; then
  PORT=8080
fi

export JAVA_OPTS="$JAVA_OPTS"
echo "[Start Script] Starting app"

# shellcheck disable=SC2086
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
#java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /application/bin/app.jar --spring.config.location=classpath:/default.properties,classpath:/application.yml,file:/application/config/application.yml
java $JAVA_OPTS org.springframework.boot.loader.JarLauncher \
  --spring.config.location=classpath:/default.properties,classpath:/application.yml,file:/application/config/application.yml \
  -Dserver.port=$PORT

exec "$@"
