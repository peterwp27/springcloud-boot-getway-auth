FROM jdk/nriet:latest
VOLUME /tmp
ADD ./target/nriet-auth-service.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 5000
ENTRYPOINT ["java","-jar","/app.jar"]