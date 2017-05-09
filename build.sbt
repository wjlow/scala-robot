name := "rea-robot"

version := "1.0"

scalaVersion := "2.12.1"

sbtVersion := "0.13.11"

val effVersion = "3.0.2"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.9.0",
  "org.atnos" %% "eff" % effVersion,
  "org.atnos" %% "eff-monix" % effVersion,
  "org.specs2" %% "specs2-core" % "3.8.6" % "test",
  "org.specs2" %% "specs2-mock" % "3.8.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-deprecation",
  "-Ypartial-unification",
  "-unchecked",
  "-feature",
  "-language:higherKinds")