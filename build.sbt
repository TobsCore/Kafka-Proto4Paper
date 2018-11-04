name := "Herr Kafka"
version := "0.1"
scalaVersion := "2.12.7"

libraryDependencies += "javax.ws.rs" % "javax.ws.rs-api" % "2.1" artifacts( Artifact("javax.ws.rs-api", "jar", "jar")) // this is a workaround for https://github.com/jax-rs/api/issues/571

libraryDependencies ++= Seq(
  "org.apache.avro" % "avro" % "1.8.2",
  "org.apache.kafka" % "kafka-streams" % "1.1.0",
  "org.apache.kafka" % "kafka-clients" % "1.1.0",
  "org.slf4j" % "slf4j-nop" % "1.7.21"
)
