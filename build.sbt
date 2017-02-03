name := """Xl-Spaceship"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(ws)

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.4.16"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-stream" % "2.4.16"

libraryDependencies += "javax.inject" % "javax.inject" % "1"

libraryDependencies += specs2 % Test

resolvers += "OSS Sonatype" at "https://repo1.maven.org/maven2/"