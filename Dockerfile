FROM openjdk:17-jdk-alpine

WORKDIR /app

EXPOSE 8080

    
ADD ./target/education-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]