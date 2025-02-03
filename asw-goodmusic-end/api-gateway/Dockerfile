FROM eclipse-temurin:21-jdk

ADD build/libs/api-gateway.jar api-gateway.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-Xmx128m", "-Xms128m", "-jar", "api-gateway.jar" ]