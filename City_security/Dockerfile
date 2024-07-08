FROM openjdk:17-jdk
WORKDIR /app
COPY build/libs/City_security-0.0.1-SNAPSHOT.war /app/City_security-0.0.1-SNAPSHOT.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "City_security-0.0.1-SNAPSHOT.war"]