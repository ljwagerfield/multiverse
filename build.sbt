name := "Multiverse"

version := "1.0"

scalaVersion := "2.10.1"

org.scalastyle.sbt.ScalastylePlugin.Settings

libraryDependencies ++= Seq(
    "org.specs2" %% "specs2" % "1.12.3" % "test"
)
