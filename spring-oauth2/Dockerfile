FROM openjdk:21
COPY target/oauth2-demo.jar oauth2-demo.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","oauth2-demo.jar"]
EXPOSE 8080
