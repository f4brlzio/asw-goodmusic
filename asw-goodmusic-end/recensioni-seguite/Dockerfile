FROM eclipse-temurin:21-jdk
ADD build/libs/recensioni-seguite.jar recensioni-seguite.jar

ENTRYPOINT [ "java", "-Xmx128m", "-Xms128m", "-jar", "recensioni-seguite.jar" ]