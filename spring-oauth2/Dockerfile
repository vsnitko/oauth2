FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -Dcheckstyle.skip=true -B

FROM openjdk:21
COPY --from=builder /app/target/oauth2-demo.jar oauth2-demo.jar
ENTRYPOINT ["java","-jar","oauth2-demo.jar"]
EXPOSE 8080
