ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "categoryTheory"

lazy val root = (project in file("."))
  .settings(
    name := "typelevel",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.4.2",
      "org.typelevel" %% "cats-effect" % "2.3.3",
      "co.fs2"        %% "fs2-core" % "2.5.3",
      "co.fs2"        %% "fs2-io" % "2.5.3",
      "io.circe" %% "circe-core" % "0.14.1",
      "io.circe" %% "circe-generic" % "0.14.1",
      "io.circe" %% "circe-parser" % "0.14.1",
      "org.typelevel" %% "cats-core" % "2.3.0",
      "org.scalatest" %% "scalatest" % "3.2.8" % Test,
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
