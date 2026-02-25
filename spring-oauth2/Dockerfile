FROM gradle:jdk21 AS builder
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle/ gradle/

RUN gradle dependencies --no-daemon

COPY src/ src/
COPY lombok.config lombok.config

RUN gradle bootJar --no-daemon

FROM openjdk:21
COPY --from=builder /app/build/libs/oauth2-demo.jar oauth2-demo.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8090","-jar","oauth2-demo.jar"]
EXPOSE 8080
