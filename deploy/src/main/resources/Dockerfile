FROM maven:latest as build
LABEL Author=YarikRevich
COPY . /java
WORKDIR /java
RUN mvn clean package -f cli/pom.xml -T100 -DskipTests -q

FROM openjdk:17-jre-slim
COPY --from=build /java/target/libs/ResourceTrackerDeployment-0.0.1-SNAPSHOT.jar /usr/local/lib/ResourceTrackerDeployment.jar
EXPOSE 10075
CMD ["/usr/local/java", "-jar", "/usr/local/lib/ResourceTrackerDeployment.jar"] 
