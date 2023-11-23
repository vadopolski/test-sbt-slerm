ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.11"

lazy val root = (project in file("."))
  .settings(
    name := "test-sbt-slerm"
  )

val sparkVersion = "3.3.2"
val logVersion = "2.17.1"
val circeVersion = "0.14.1"
val postgresVersion = "42.6.0"
val chimneyVersion = "0.6.1"


libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % postgresVersion,
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
"io.scalaland" %% "chimney" % chimneyVersion,
  "org.apache.logging.log4j" % "log4j-api" % logVersion,
  "org.apache.logging.log4j" % "log4j-core" % logVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % "2.24.4",
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % "2.24.4"


)



