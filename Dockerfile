FROM gradle:8.7-jdk AS build
WORKDIR /app
COPY . /app
RUN gradle clean build -x test
FROM openjdk:17-jdk
EXPOSE 8080
COPY --from=build /app/build/libs/City_security-0.0.1-SNAPSHOT.war /app/City_security-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java", "-jar", "City_security-0.0.1-SNAPSHOT.war"]