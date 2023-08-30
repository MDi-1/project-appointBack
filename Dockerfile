FROM ibm-semeru-runtimes:open-11-jre-focal
COPY ./build/libs/project-appointBack.jar app.jar
ENV _JAVA_OPTIONS="-XX:MaxRAM=70m"
ENV ABSTRACT_KEY="a165470693d04dac8edac18f8d934947"
# ENTRYPOINT ["java","-jar","/app.jar"]
CMD java $_JAVA_OPTIONS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -Dspring.datasource.url=$SPRING_DATASOURCE_URL -Dspring.liquibase.url=$SPRING_LIQUIBASE_URL -Dspring.datasource.username=$SPRING_DATASOURCE_USERNAME -Dspring.datasource.password=$SPRING_DATASOURCE_PASSWORD -jar app.jar
