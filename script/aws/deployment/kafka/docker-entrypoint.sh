#!/bin/bash

_term() {
    echo "🚨 Termination signal received...";
    kill -TERM "$child" 2>/dev/null
}

trap _term SIGINT SIGTERM

properties_file=/opt/kafka/config/kraft/server.properties;

echo "==> Applying configuration...";

echo "listeners=CONTROLLER://0.0.0.0:19092,INTERNAL://0.0.0.0:9092,EXTERNAL://0.0.0.0:9093" >> $properties_file;
echo "advertised.listeners=INTERNAL://$KRAFT_CONTAINER_HOST_NAME:9092,EXTERNAL://$KRAFT_CONTAINER_HOST_NAME:9093" >> $properties_file;
echo "inter.broker.listener.name=EXTERNAL" >> $properties_file;
echo "listener.security.protocol.map=CONTROLLER:PLAINTEXT,INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT" >> $properties_file;

echo "==> ✅ Enivronment variables applied.";


echo "==> Setting up Kafka storage...";
export suuid=$(/opt/kafka/bin/kafka-storage.sh random-uuid);
/opt/kafka/bin/kafka-storage.sh format -t $suuid -c /opt/kafka/config/kraft/server.properties;
echo "==> ✅ Kafka storage setup.";


echo "==> Starting Kafka server...";
/opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/kraft/server.properties &
child=$!
echo "==> ✅ Kafka server started.";

if [ -z $KRAFT_CREATE_TOPICS ]; then
    echo "==> No topic requested for creation.";
else
    echo "==> Creating topics...";
    /opt/wait-for-it.sh localhost:9093;

    pc=1
    if [ $KRAFT_PARTITIONS_PER_TOPIC ]; then
        pc=$KRAFT_PARTITIONS_PER_TOPIC
    fi

    for i in $(echo $KRAFT_CREATE_TOPICS | sed "s/,/ /g")
    do
        /opt/kafka/bin/kafka-topics.sh --create --topic "$i" --partitions "$pc" --replication-factor 1 --bootstrap-server localhost:9093;
    done
    echo "==> ✅ Requested topics created.";
fi

wait "$child";
