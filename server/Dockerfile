FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY . .
RUN ./mvnw clean install

CMD ./mvnw spring-boot:run