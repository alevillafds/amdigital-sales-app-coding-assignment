FROM openjdk:11-jre-slim
VOLUME /tmp
ADD sales-0.0.1.RELEASE.jar app.jar
EXPOSE 80
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
