FROM jdk/nriet:latest
VOLUME /tmp
ADD ./target/nriet-config.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app.jar"]