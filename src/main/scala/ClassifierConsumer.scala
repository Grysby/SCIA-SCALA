import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

import java.util
import java.util.{Calendar, Properties}
import java.text.SimpleDateFormat
import scala.concurrent.duration.DurationInt
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import DroneReportObj._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object ClassifierConsumer extends App {

  // Class fields
  var key = true
  val format = new SimpleDateFormat("y-M-d")
  val pollDuration = java.time.Duration.ofNanos(1.seconds.toNanos)
  val readingTopic = "DroneReports"
  val writingTopic = "AlertingReports"


  // Main loop
  def ReportdReaderLoop(consumer: KafkaConsumer[String, String], producer: KafkaProducer[String, String], threshold: Int) {
    while (key) {

      val droneRecords = consumer.poll(pollDuration)

      droneRecords.forEach { msg =>
        val (pos, problemCitizens) = decode[DroneReport](msg.value()) match {
          case Right(report) => ProblemCitizens(report, threshold)
          case Left(_) => (Nil, Nil)
        }

        problemCitizens
          .map(citizen => (citizen._1, citizen._2, pos))
          .map(entry => new ProducerRecord[String, String](writingTopic, entry.asJson.noSpaces))
          .foreach(records => producer.send(records))
      }

      Thread.sleep(3000L)
    }
  }

  // Graceful shutdown
  def StopLoop(): Unit = {
    key = false
  }

  // Properties for Kafka consumer
  val propsConsumer = new Properties()
  propsConsumer.put("bootstrap.servers", "localhost:9092")
  propsConsumer.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  propsConsumer.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  propsConsumer.put("group.id", "something")

  // Instantiate consumer
  val consumer = new KafkaConsumer[String, String](propsConsumer)
  consumer.subscribe(util.Collections.singletonList(readingTopic))

  // Properties for Kafka producer
  val propsProducer: Properties = new Properties()
  propsProducer.put("bootstrap.servers", "localhost:9092")
  propsProducer.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  propsProducer.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  propsProducer.put("acks", "all")

  // Instantiate producer
  val producer = new KafkaProducer[String, String](propsProducer)

  // Main execution
  ReportdReaderLoop(consumer, producer, 35)
}