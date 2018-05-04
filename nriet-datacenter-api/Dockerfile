FROM jdk/nriet:latest
VOLUME /tmp
ADD ./target/nriet-datacenter-api.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8070
ENTRYPOINT ["java","-jar","/app.jar"]