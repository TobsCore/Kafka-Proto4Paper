import java.lang.Long
import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.kafka.common.serialization._
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.{
  KStream,
  KTable,
  Materialized,
  Produced
}
import org.apache.kafka.streams.state.KeyValueStore
import org.apache.kafka.streams.{
  KafkaStreams,
  StreamsBuilder,
  StreamsConfig,
  Topology
}

import scala.collection.JavaConverters.asJavaIterableConverter

class WordCounter(inputTopic: String, outputTopic: String) {
  val config: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
          Serdes.String().getClass)
    p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
          Serdes.String().getClass)
    p
  }

  def countNumberOfWords(storeName: String): Topology = {
    val builder: StreamsBuilder = new StreamsBuilder()
    val textLines: KStream[String, String] = builder.stream(inputTopic)

    // Splits the words of the input into an array of words.
    val wordCounts: KTable[String, Long] = textLines
      .flatMapValues { textLine =>
        textLine.toLowerCase.split("\\W+").toIterable.asJava
      }
      .groupBy((_, word) => word)
      .count(
        Materialized
          .as(storeName)
          .asInstanceOf[Materialized[String,
                                     Long,
                                     KeyValueStore[Bytes, Array[Byte]]]])

    // Stream the newly calculated word count to the output
    wordCounts
      .toStream()
      .to(outputTopic, Produced.`with`(Serdes.String(), Serdes.Long()))
    builder.build()
  }
}

object WordCountApp extends App {
  val wordCounter = new WordCounter("word-count", "word-count-result")

  val streams: KafkaStreams = new KafkaStreams(
    wordCounter.countNumberOfWords("counts-store"),
    wordCounter.config)
  streams.start()

  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    streams.close(10, TimeUnit.SECONDS)
  }))
}
