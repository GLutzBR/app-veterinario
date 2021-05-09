# Dockerfile

FROM  maven:3.8.1-jdk-11-slim

MAINTAINER  Gustavo Antonio Lutz de Matos <gustavo.almatos@gmail.com>

RUN mkdir /src
RUN mkdir /app

COPY . /src

WORKDIR /src

RUN mvn -DskipTests=true install -f pom.xml

RUN cp ./target/appVeterinario-0.0.1-SNAPSHOT.jar /app

WORKDIR /app

RUN rm -Rf /src

ENTRYPOINT ["java", "-jar", "./appVeterinario-0.0.1-SNAPSHOT.jar"]
