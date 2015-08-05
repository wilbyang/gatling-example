import _root_.sbt.Keys._
import _root_.sbt._
import io.gatling.sbt.{GatlingKeys, GatlingPlugin}

object Build extends Build {
  final val DependsConfigure = "test->test;compile->compile"

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  ///////////////////////////////////////////////////////////////
  // Gatling example
  ///////////////////////////////////////////////////////////////
  lazy val pressure = Project("gatling-example", file("."))
    .enablePlugins(GatlingPlugin)
    .settings(
      version := "0.0.1",
      homepage := Some(new URL("https://github.com/yangbajing/gatling-example")),
      organization := "cn.socialcredits",
      organizationHomepage := Some(new URL("http://www.yangbajing.me")),
      startYear := Some(2015),
      scalaVersion := "2.11.7",
      scalacOptions := Seq(
        "-encoding", "utf8",
        //"-Ylog-classpath",
        "-feature",
        "-unchecked",
        "-deprecation",
        "-explaintypes",
        "-Yno-adapted-args",
        "-Ywarn-dead-code",
        "-Ywarn-unused"
      ),
      javacOptions := Seq(
        "-encoding", "utf8",
        "-deprecation",
        "-Xlint:unchecked",
        "-Xlint:deprecation"
      ),
      resolvers ++= Seq(
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
        "releases" at "http://oss.sonatype.org/content/repositories/releases",
        "maven.mirrorid" at "http://mirrors.ibiblio.org/pub/mirrors/maven2/",
        "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
        "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"),
      offline := true,
      description := "social-credits-pressure-suite",
      javaOptions in GatlingKeys.Gatling := GatlingKeys.overrideDefaultJavaOptions("-Xms1024m", "-Xmx2048m"),
      libraryDependencies ++= Seq(
        _bson,
        _gatlingHighcharts,
        _gatlingTest))

  val varGatling = "2.1.7"
  val _bson = "org.mongodb" % "bson" % "3.0.3" % "compile"
  val _gatlingHighcharts = "io.gatling.highcharts" % "gatling-charts-highcharts" % varGatling % "provided,test"
  val _gatlingTest = "io.gatling" % "gatling-test-framework" % varGatling % "test"

}

