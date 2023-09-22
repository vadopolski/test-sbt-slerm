ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "test-sbt-slerm"
  )

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.6.0",
  "org.apache.spark" %% "spark-core" % "3.2.0",
  "org.scalaxb" %% "scalaxb" % "1.11.1"

)


dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"




