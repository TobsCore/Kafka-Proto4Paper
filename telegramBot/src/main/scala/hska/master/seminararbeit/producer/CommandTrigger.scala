package hska.master.seminararbeit.producer

import java.util.Properties
import java.util.concurrent.Future

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.streams.StreamsConfig
import org.joda.time.DateTime

object CommandTrigger {

  val config: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "telegram-bot-command-trigger-producer")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    p.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer")
    p.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    p
  }

  val producer = new KafkaProducer[Int, String](config)
  val topic = "telegram-command-trigger"

  def produceTriggerMessage(userId: Int, message: String = ""): Future[RecordMetadata] = {
    val fmt = "HH:mm:ss.SSS"
    val triggerDate = DateTime.now.toString(fmt)
    val customMessage = if (message.isEmpty) { triggerDate } else { message + "|" + triggerDate }
    val data = new ProducerRecord[Int, String](topic, userId, customMessage)
    producer.send(data, ((_, _) => {}): Callback)
  }
}
