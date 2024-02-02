FROM ubuntu:latest AS build

RUN apt-get update

# install jdk
RUN apt-get install openjdk-17-jdk -y

# copy project
COPY . .

# install maven
RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/AGIS-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
