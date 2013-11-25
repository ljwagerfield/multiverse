name := "Multiverse"

version := "1.0"

scalacOptions += "-unchecked"

scalacOptions += "-feature"

scalacOptions += "-deprecation"

org.scalastyle.sbt.ScalastylePlugin.Settings

libraryDependencies += "org.specs2" %% "specs2" % "2.3.4" % "test"

libraryDependencies += "commons-validator" % "commons-validator" % "1.4.0"

libraryDependencies += "commons-io" % "commons-io" % "1.3.2"

libraryDependencies += "commons-codec" % "commons-codec" % "1.8"
