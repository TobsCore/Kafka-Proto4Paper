name := "Herr Kafka"
version := "0.1"
scalaVersion := "2.12.7"

lazy val deps =
  new {

    val rsAPI = "javax.ws.rs" % "javax.ws.rs-api" % "2.1" artifacts (Artifact(
      "javax.ws.rs-api",
      "jar",
      "jar")) // this is a workaround for https://github.com/jax-rs/api/issues/571
    val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
    val nscalaTime = "com.github.nscala-time" %% "nscala-time" % "2.20.0"
    val avro = "org.apache.avro" % "avro" % "1.8.2"
    val kafkaStreams = "org.apache.kafka" % "kafka-streams" % "1.1.0"
    val kafkaClients = "org.apache.kafka" % "kafka-clients" % "1.1.0"
    val telegramBot = "info.mukel" %% "telegrambot4s" % "3.0.15"
  }

lazy val commonDependencies = Seq(
  deps.logback,
  deps.scalaLogging,
  deps.kafkaStreams
)

lazy val telegramBot = project
  .in(file("telegramBot"))
  .settings(
    name := "telegram-bot",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      deps.rsAPI,
      deps.telegramBot,
      deps.nscalaTime,
      deps.kafkaClients
    )
  )

lazy val wc = project
  .in(file("wc"))
  .settings(
    name := "wc",
    settings,
    libraryDependencies ++= commonDependencies
  )
  .dependsOn(
    )

lazy val settings = Seq()

// In order to run the application multiple times in sbt
run / fork := true

// Stops the program from running in sbt (by calling ctrl+c) and doesn't stop sbt
Global / cancelable := true
