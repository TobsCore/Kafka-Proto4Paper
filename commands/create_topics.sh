#! /bin/bash
kafka-topics --create --zookeeper localhost:2182 --partitions 1 --replication-factor 1 --topic telegram-command-trigger
kafka-topics --create --zookeeper localhost:2182 --partitions 1 --replication-factor 1 --topic word-count
kafka-topics --create --zookeeper localhost:2182 --partitions 1 --replication-factor 1 --topic word-count-result
