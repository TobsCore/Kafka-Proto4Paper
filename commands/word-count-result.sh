#! /bin/bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic word-count-result --property print.key=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
