FROM maven:3-amazoncorretto-17 as build
WORKDIR /app
COPY src src
COPY pom.xml .
RUN mvn clean install package

FROM amazoncorretto:17-alpine
COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]