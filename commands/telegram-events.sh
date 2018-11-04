#! /bin/bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic telegram-command-trigger --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property key.deserializer=org.apache.kafka.common.serialization.IntegerDeserializer
