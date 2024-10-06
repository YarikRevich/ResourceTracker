FROM openjdk:8u292-slim-buster as kafka

WORKDIR /opt

ARG kafkaversion=3.6.2
ARG scalaversion=2.12

ENV KRAFT_CREATE_TOPICS=
ENV KRAFT_PARTITIONS_PER_TOPIC=

RUN apt update \
    && apt install -y --no-install-recommends wget

RUN wget https://mirrors.ocf.berkeley.edu/apache/kafka/${kafkaversion}/kafka_${scalaversion}-${kafkaversion}.tgz -O kafka.tgz \
    && tar xvzf kafka.tgz \
    && mv kafka_${scalaversion}-${kafkaversion} kafka

WORKDIR /opt/kafka

COPY ./configs/server.properties ./config/kraft
COPY ./*.sh .

EXPOSE 9092 9093

FROM debian:bullseye-slim as python

WORKDIR /opt

COPY --from=kafka /opt .

LABEL org.opencontainers.image.source https://github.com/YarikRevich/ResourceTracker
LABEL org.opencontainers.image.licenses MIT

COPY requirements.txt ./

RUN apt update -y && \
    apt install python3 -y && \
    apt install python3-pip -y

RUN mkdir -p /usr/share/man/man1 /usr/share/man/man2

RUN apt-get update && \
apt-get install -y --no-install-recommends \
        openjdk-11-jre

RUN pip3 install --no-cache-dir -r requirements.txt

COPY . .

EXPOSE 8080 8080

CMD [ "python3", "./main.py" ]
