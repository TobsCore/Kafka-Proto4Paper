package hska.master.seminararbeit.botcommands

import hska.master.seminararbeit.producer.{CommandTrigger, WordCounter}
import info.mukel.telegrambot4s.api.TelegramBot
import info.mukel.telegrambot4s.api.declarative.Commands

trait KafkaAction extends Commands {
  _: TelegramBot =>

  onCommand("/start") { implicit msg =>
    using(_.from) { user =>
      val username = user.firstName
      reply(s"Hey $username, you can trigger events by using the /kafka command. Have fun!")
    }
  }

  onCommand("/kafka") { implicit msg =>
    using(_.from) { user =>
      val userID = user.id
      logger.debug(s"$userID triggered an event!")
      CommandTrigger.produceTriggerMessage(userID)
      reply("Event triggered!")
    }
  }

  onCommand("/about") { implicit msg =>
    reply(
      "The 'Herr Kafka' telegram bot was specially developed to show an interactive example of how apache kafka uses topics to react to different kinds of messages. It is written by Tobias Kerst.")
  }

  onMessage { implicit msg =>
    using(_.text) { userMessage =>
      if (!userMessage.startsWith("/")) {
        val userID = msg.from.get.id
        // Is a chat message and not a command
        logger.debug(s"$userID entered custom message <$userMessage>")
        reply("Thank you for your contribution.")
        WordCounter.addUserWordsToTopic(userMessage)
      }
    }
  }
}
