name := "rea-robot"

version := "1.0"

scalaVersion := "2.11.8"

val scalazVersion = "7.1.0"

libraryDependencies ++= Seq("org.scalactic" %% "scalactic" % "2.2.6",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "org.scalaz" %% "scalaz-core" % scalazVersion
)