FROM jdk/nriet:latest
VOLUME /tmp
ADD ./target/nriet-eureka.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app.jar"]