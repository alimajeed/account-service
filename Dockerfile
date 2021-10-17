FROM openjdk:latest

COPY target/accountservice-0.0.1-SNAPSHOT.jar accountservice.jar

ENTRYPOINT ["java","-jar","accountservice.jar"]

EXPOSE 8080