# SCIA-SCALA
Projet SCALA, SCIA EPITA

# Installation

In order to use this application, you will need to have [https://www.confluent.io/download](confluent-6.0.2).
After downloading the apllication you will need to open a terminal and execute those shell commands:

## Setting up the Zookeeper, the kafka-server and the schema-registry

- cd /path/to/the/downloaded/confluent
- ./bin/zookeeper-server-start ./etc/kafka/zookeeper.properties
- ./bin/kafka-server-start ./etc/kafka/server.properties (on an other terminal)
- ./bin/schema-registry-start ./etc/schema-registry/schema-registry.properties

## Create the topic

Before running our code you will need to create two topics, don't worry just open a new terminal and run the following commands: 
- cd /path/to/the/downloaded/confluent
- ./bin/kafka-console-consumer --topic  DroneReports --from-beginning --bootstrap-server localhost:9092 

On an other terminal execute:
- cd /path/to/the/downloaded/confluent
- ./bin/kafka-console-consumer --topic  AlertingReports --from-beginning --bootstrap-server localhost:9092

## Running 

Now that everything is set up, you can open your favorite IDE (we recommend using Intellij) and execute the ReportProducer first and then the ClassifierConsumer.

# Storing to your private Google Platforme Cloud

In order to connect our solution to your own google platform you will first need confluent-hub, then you will need to download the plugin gcs-connector by running
******
