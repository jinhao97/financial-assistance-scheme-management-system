FROM eclipse-temurin:17
COPY target/*jar assignment.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","assignment.jar"]