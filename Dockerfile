FROM openjdk:latest

RUN mkdir -p /opt/app/
COPY ./target/stocks-manager-0.0.1-SNAPSHOT.jar /opt/app/smapp.jar
EXPOSE 80

CMD ["java", "-jar", "/opt/app/smapp.jar"]