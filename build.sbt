name := "Multiverse"

version := "1.0"

scalaVersion := "2.10.1"

scalacOptions += "-unchecked"

scalacOptions += "-feature"

scalacOptions += "-deprecation"

org.scalastyle.sbt.ScalastylePlugin.Settings

libraryDependencies += "org.specs2" %% "specs2" % "1.12.3" % "test"

libraryDependencies += "commons-validator" % "commons-validator" % "1.4.0"

libraryDependencies += "commons-codec" % "commons-codec" % "1.8"
