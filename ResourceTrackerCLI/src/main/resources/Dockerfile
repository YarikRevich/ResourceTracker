FROM maven:latest as build
LABEL Author=YarikRevich
COPY ResourceTrackerCLI/* /java
WORKDIR /java
RUN mvn clean package -T100 -DskipTests -q

FROM openjdk:17-jre-slim
COPY --from=build /java/target/libs/ResourceTracker-0.0.1-SNAPSHOT.jar /usr/local/lib/ResourceTracker.jar
EXPOSE 10075
CMD ["/usr/local/java", "-jar", "/usr/local/lib/ResourceTracker.jar"] 