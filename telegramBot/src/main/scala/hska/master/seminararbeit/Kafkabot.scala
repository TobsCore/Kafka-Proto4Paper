package hska.master.seminararbeit

// Is used to write syntax such as '10 seconds' in akka calls. Otherwise warnings would be thrown
// during compilation.
import hska.master.seminararbeit.botcommands.KafkaAction
import info.mukel.telegrambot4s.api.declarative.{Callbacks, Commands}
import info.mukel.telegrambot4s.api.{Polling, TelegramBot}
import org.apache.kafka.streams.KafkaStreams

import scala.io.Source
import scala.language.postfixOps

class Kafkabot()
    extends TelegramBot
    with Polling
    with Commands
    with Callbacks

    // These are the actual commands
    with KafkaAction {

  lazy val token: String = scala.util.Properties
    .envOrNone("BOT_TOKEN")
    .getOrElse(Source.fromFile("kafkabot.token").getLines().mkString)

}

object Kafkabot extends App {
  val bot = new Kafkabot()
  bot.run()
}
