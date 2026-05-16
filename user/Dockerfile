FROM eclipse-temurin:25-jdk
WORKDIR /app
EXPOSE 8083
COPY target/user.jar user.jar
ENTRYPOINT ["java", "-jar", "user.jar"]