FROM jdk/nriet:latest
VOLUME /tmp
ADD ./target/nriet-gateway.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8060
ENTRYPOINT ["java","-jar","/app.jar"]