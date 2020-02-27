FROM java:8
ADD /target/charity-0.0.1-SNAPSHOT.war charity.war
ENTRYPOINT ["java", "-jar", "/charity.war"]
