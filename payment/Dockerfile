FROM openjdk:25
EXPOSE 8082
COPY target/payment.jar payment.jar
ENTRYPOINT ["java", "-jar", "payment.jar"]