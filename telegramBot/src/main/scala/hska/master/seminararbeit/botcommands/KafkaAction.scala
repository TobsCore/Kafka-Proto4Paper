package hska.master.seminararbeit.botcommands

import hska.master.seminararbeit.producer.{CommandTrigger, WordCounter}
import info.mukel.telegrambot4s.api.TelegramBot
import info.mukel.telegrambot4s.api.declarative.Commands

trait KafkaAction extends Commands {
  _: TelegramBot =>

  onCommand("/start") { implicit msg =>
    using(_.from) { user =>
      reply(s"Hey ${user.firstName}, you can trigger events by using the /kafka command. Have fun!")
    }
  }

  onCommand("/kafka") { implicit msg =>
    using(_.from) { user =>
      val userID = user.id
      logger.debug(s"$userID triggered an event!")

      // Send message to kafka topic
      CommandTrigger.produceTriggerMessage(userID)
      reply("Event triggered!")
    }
  }

  onMessage { implicit msg =>
    // use userMessage as a shorthand to get the message's contents
    using(_.text) { userMessage =>
      // Check if the message is not a command
      if (!userMessage.startsWith("/")) {
        val userID = msg.from.get.id
        logger.debug(s"$userID entered custom message <$userMessage>")

        // Send message to kafka topic
        WordCounter.addUserWordsToTopic(userMessage)
        reply("Thank you for your contribution.")
      }
    }
  }

  onCommand("/about") { implicit msg =>
    reply(
      "The 'Herr Kafka' telegram bot was specially developed to show an interactive example of how apache kafka uses topics to react to different kinds of messages. It is written by Tobias Kerst.")
  }
}
