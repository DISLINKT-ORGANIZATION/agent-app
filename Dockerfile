FROM adoptopenjdk:11-jre-hotspot
COPY "target/agent-app.jar" agent-app.jar
ENTRYPOINT ["java", "-jar", "agent-app.jar"]