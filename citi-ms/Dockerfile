FROM openjdk:8
#RUN mkdir /myapp
COPY /target/citi-ms-0.0.1-Dev.jar  citi.jar
EXPOSE 11111
ENTRYPOINT ["java", "-jar", "citi.jar"]
