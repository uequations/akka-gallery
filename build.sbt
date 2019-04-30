lazy val akkaHttpVersion = "10.1.8"
lazy val akkaVersion = "2.5.22"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.uequations",
      scalaVersion := "2.12.7"
    )),
    name := "akka-ueq-scala",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.google.inject" % "guice" % "4.2.2",
      "org.glassfish.jersey.core" % "jersey-server" % "2.28",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "com.typesafe.play" %% "play-json" % "2.7.3",
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )
