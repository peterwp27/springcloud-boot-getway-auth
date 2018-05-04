FROM jdk/nriet:latest
VOLUME /tmp
ADD ./target/nriet-zipkin.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 9411
ENTRYPOINT ["java","-jar","/app.jar"]