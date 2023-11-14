FROM openjdk:17-alpine
EXPOSE 8083
ADD target/rental-app.jar rental-app.jar
ENTRYPOINT ["java","-jar","/rental-app.jar"]