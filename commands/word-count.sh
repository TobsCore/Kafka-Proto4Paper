#! /bin/bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic word-count --property --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
