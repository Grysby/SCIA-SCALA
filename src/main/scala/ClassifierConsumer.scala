import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

import java.util
import java.util.{Calendar, Properties}
import java.text.SimpleDateFormat
import scala.concurrent.duration.DurationInt
import net.liftweb.json._
import DroneReportObj._

object ClassifierConsumer extends App {

  var key = true
  val format = new SimpleDateFormat("y-M-d")
  val pollDuration = java.time.Duration.ofNanos(1.seconds.toNanos)

  // Properties for Kafka producer
  val props = new Properties()

  def SetupProps() {
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "something")
  }

  // Instantiate consumer
  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(util.Collections.singletonList("DroneReports"))

  def ReportdReaderLoop() {
    while (key) {
      val records = consumer.poll(pollDuration)
      records.forEach { msg =>
        val json = JsonParser.parse(msg.value())
        val drone = json.extract[DroneReport]
        drone.apply(DefaultFormats, Manifest[DroneReport])
      }
      Thread.sleep(3000L)
    }
  }

  def StopLoop(): Unit = {
    key = false
  }

  // Main execution
  SetupProps()
  ReportdReaderLoop()
}