FROM openjdk:23-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ecomm-inventory.jar
ENTRYPOINT ["java","-jar","/ecomm-inventory.jar"]
EXPOSE 8094