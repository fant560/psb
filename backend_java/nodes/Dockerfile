FROM amazoncorretto:11-alpine-jdk
MAINTAINER fant560.com
COPY target/nodes-0.0.1-SNAPSHOT.jar nodes-1.0.0.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/nodes-1.0.0.jar"]