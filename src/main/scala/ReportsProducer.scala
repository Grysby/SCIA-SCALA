import DroneReportObj._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import java.util.Properties

object ReportsProducer extends App {

  // Name of the topic that will be used to put messages
  val topic = "DroneReports"
  var key = true

  // Main report generator loop
  def ReportGeneratorLoop(producer: KafkaProducer[String, String]) {
    while (key) {
      val report = GenerateReport(10, 10)
      val record = new ProducerRecord[String, String](topic, BuildReport(report))
      producer.send(record)
      Thread.sleep(3000L)
    }
  }

  // Graceful shutdown
  def StopLoop(): Unit = {
    key = false
  }

  // Properties for Kafka producer
  val props: Properties = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("acks", "all")

  // Instantiate producer
  val producer = new KafkaProducer[String, String](props)

  // Main execution loop
  ReportGeneratorLoop(producer);
}