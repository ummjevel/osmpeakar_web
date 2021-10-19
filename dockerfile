FROM adoptopenjdk/openjdk14
MAINTAINER mseagle2023@gmail.com
VOLUME /tmp
EXPOSE 9000
ADD build/libs/osmapi-1.0-SNAPSHOT.jar osmapi.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/osmapi.jar"]

RUN apt-get update
RUN apt-get install -y vim
