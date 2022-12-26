FROM openjdk:20
COPY ../target/oauth2-demo.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]
