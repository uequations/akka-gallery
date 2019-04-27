package com.uequations

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object UEQServer extends App {

  implicit lazy val system = ActorSystem()
  implicit lazy val materializer = ActorMaterializer()(system)

}
