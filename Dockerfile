# Dockerfile

FROM  maven:3.8.1-jdk-11-slim

MAINTAINER  Gustavo Antonio Lutz de Matos <gustavo.almatos@gmail.com>

RUN mkdir /src
RUN mkdir /app

COPY . /src

WORKDIR /src

RUN mvn -DskipTests=true install -f pom.xml

RUN cp ./target/appVeterinario-2.0.0.war /app

WORKDIR /app

RUN rm -Rf /src

ENTRYPOINT ["java", "-jar", "./appVeterinario-2.0.0.war"]
