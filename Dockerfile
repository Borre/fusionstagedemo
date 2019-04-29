FROM openjdk:8-jdk-alpine
WORKDIR /app
ADD build/libs/demoFusionStage-0.0.1.jar /app
EXPOSE 8080
CMD ["java", "-jar", "/app/demoFusionStage-0.0.1.jar"]