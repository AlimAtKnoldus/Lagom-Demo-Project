name := "name-2"

version := "0.1"

ThisBuild / scalaVersion := "2.13.6"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test

lazy val `name-2` = (project in file("."))
  .aggregate(`name-2-api`,`name-2-impl` )

lazy val `name-2-api` = (project in file("name-2-api"))
  .settings(

    ThisBuild / lagomKafkaEnabled := false,
    ThisBuild / lagomCassandraEnabled := false,
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      "com.wix" %% "accord-core" % "0.7.4"
    )
  )

lazy val `name-2-impl` = (project in file("name-2-impl"))
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
  .dependsOn(`name-2-api`)
