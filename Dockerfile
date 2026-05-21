FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S library && adduser -S library -G library
COPY --from=build /app/target/*.jar app.jar
RUN mkdir -p /app/uploads && chown -R library:library /app

USER library
EXPOSE 8080

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
  CMD wget -q --spider http://localhost:8080/v3/api-docs || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
