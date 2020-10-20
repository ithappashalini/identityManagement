FROM openjdk:13-alpine3.10
EXPOSE 8096
ENV JAVA_OPTS " "
COPY target/identityManagementApi.jar /app/identityManagementApi.jar
ENTRYPOINT java $JAVA_OPTS -jar /app/identityManagementApi.jar