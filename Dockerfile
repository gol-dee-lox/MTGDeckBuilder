# Stage 1: Build the application
FROM gradle:8.4.0-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN ./gradlew build --no-daemon --stacktrace

# Stage 2: Run the application
FROM eclipse-temurin:21-jre
VOLUME /tmp
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]