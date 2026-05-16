FROM eclipse-temurin:25-jdk
WORKDIR /app
EXPOSE 8082
COPY target/payment.jar payment.jar
ENTRYPOINT ["java", "-jar", "payment.jar"]