# SCIA-SCALA

Project made by three EPITA's students. It is our implementation of the peaceland project

## Installation

In order to use this application, you will need to have [confluent](https://www.confluent.io/download).
After downloading the apllication follow the instruction.

### Setting up the Zookeeper, the kafka-server and the schema-registry

- ***cd*** /path/to/the/downloaded/confluent
- ***./bin/zookeeper-server-start*** ./etc/kafka/zookeeper.properties
- ***./bin/kafka-server-start*** ./etc/kafka/server.properties (on an other terminal)
- ***./bin/schema-registry-start*** ./etc/schema-registry/schema-registry.properties

### Create the topic

Before running our code you will need to create two topics, don't worry just open a new terminal and run the following commands: 
- cd /path/to/the/downloaded/confluent
- ./bin/kafka-console-consumer --topic  DroneReports --from-beginning --bootstrap-server localhost:9092 

On an other terminal execute:
- cd /path/to/the/downloaded/confluent
- ./bin/kafka-console-consumer --topic  AlertingReports --from-beginning --bootstrap-server localhost:9092

## Running 

Now that everything is set up, you can open your favorite IDE (we recommend using Intellij) and execute the ***ReportProducer*** first and then the ***ClassifierConsumer***.

## Storing to your private Google Cloud Platform

In order to connect our solution to your own google platform you will first need confluent-hub, then you will need to download the plugin gcs-connector by running
***sudo confluent-hub install confluentinc/kafka-connect-gcs:latest***

Once this is done you will have to modify the given gcs.properties file, by entering your private-key

Finally just enter in a terminal :  
- cd /path/to/the/downloaded/confluent
- ./bin/connect-standalone ./etc/kafka/connect-standalone.properties ./path/to/gcs-sink.properties
