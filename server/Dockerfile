FROM openjdk:17

WORKDIR /app

COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean install

CMD ./mvnw spring-boot:run