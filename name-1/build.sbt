name := "name-1"

version := "0.1"

ThisBuild / scalaVersion := "2.13.6"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test

lazy val `name-1` = (project in file("."))
  .aggregate(`name-1-api`, `name-1-impl` )

lazy val `name-1-api` = (project in file("name-1-api"))
  .settings(
    ThisBuild / lagomKafkaEnabled := false,
    ThisBuild / lagomCassandraEnabled := false,
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      "com.wix" %% "accord-core" % "0.7.4"
    )
  )

lazy val `name-1-impl` = (project in file("name-1-impl"))
  .enablePlugins(LagomScala)
  .settings(
    ThisBuild / lagomKafkaEnabled := false,
    ThisBuild / lagomCassandraEnabled := false,
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceJdbc,
      lagomScaladslKafkaBroker,
      lagomScaladslAkkaDiscovery,
      lagomScaladslTestKit,
      macwire,
      scalaTest,
      "com.wix" %% "accord-core" % "0.7.4"
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`name-1-api`)
