package hska.master.seminararbeit.producer

import java.util.Properties
import java.util.concurrent.Future

import com.typesafe.scalalogging.Logger
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.streams.StreamsConfig
import org.joda.time.DateTime

object WordCounter {

  val logger = Logger(getClass)

  val config: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "telegram-bot-word-count-producer")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    p.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    p.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    p
  }

  val producer = new KafkaProducer[String, String](config)
  val topic = "word-count"

  def addUserWordsToTopic(message: String): Future[RecordMetadata] = {
    val data = new ProducerRecord[String, String](topic, message)
    logger.trace(s"Sending $message to broker")
    producer.send(data, ((_, _) => {}): Callback)
  }
}
