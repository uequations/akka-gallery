package com.uequations

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.google.inject.{Guice, Module}
import com.uequations.api.AppRestModule
import scala.collection.JavaConverters._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  implicit lazy val system = ActorSystem()
  implicit lazy val materializer = ActorMaterializer()(system)

  val appRestModule = new AppRestModule

  protected def modules: Seq[Module] = Seq(appRestModule)

  val injector = Guice.createInjector(modules.asJava)

  Await.result(system.whenTerminated, Duration.Inf)
}
