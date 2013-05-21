import sbt._
import Defaults._

scalaVersion := "2.9.2"

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.2.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.0-SNAPSHOT")

resolvers ++= Seq("sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
                  "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/")
