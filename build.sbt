name := "Herr Kafka"
version := "0.1"
scalaVersion := "2.12.7"

libraryDependencies += "javax.ws.rs" % "javax.ws.rs-api" % "2.1" artifacts( Artifact("javax.ws.rs-api", "jar", "jar")) // this is a workaround for https://github.com/jax-rs/api/issues/571
//libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.21"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.20.0"

libraryDependencies ++= Seq(
  "org.apache.avro" % "avro" % "1.8.2",
  "org.apache.kafka" % "kafka-streams" % "1.1.0",
  "org.apache.kafka" % "kafka-clients" % "1.1.0",
  "info.mukel" %% "telegrambot4s" % "3.0.15",
)


mainClass in Compile := Some("hska.master.seminararbeit.Kafkabot")

// In order to run the application multiple times in sbt
run / fork := true

// Stops the program from running in sbt (by calling ctrl+c) and doesn't stop sbt
Global / cancelable := true
