FROM docker.io/bitnami/java:latest
RUN apt update && apt upgrade -y && apt install -y maven
COPY . /app/
WORKDIR /app/
RUN mvn package -Dmaven.test.skip=true
RUN mv ./target/track-and-trail-0.0.1-SNAPSHOT.jar /usr/src/track-and-trail-0.0.1-SNAPSHOT.jar
CMD ["java" ,"-jar", "/usr/src/track-and-trail-0.0.1-SNAPSHOT.jar"]
