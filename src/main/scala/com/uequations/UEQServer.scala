package com.uequations

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object UEQServer extends App {

  implicit lazy val system = ActorSystem()
  implicit lazy val materializer = ActorMaterializer()(system)

  Await.result(system.whenTerminated, Duration.Inf)
}
