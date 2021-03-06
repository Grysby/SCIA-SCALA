name := "SCIA-SCALA"

version := "1"

scalaVersion := "2.13.6"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "net.liftweb" %% "lift-json" % "3.4.3+"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.7.0"
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.7.0"

libraryDependencies += "com.github.azakordonets" %% "fabricator" % "2.1.9"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % "0.14.1")
