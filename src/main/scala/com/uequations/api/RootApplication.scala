package com.uequations.api

import java.util

import javax.ws.rs.core.Application
import javax.ws.rs.ext.ExceptionMapper
import scala.collection.JavaConverters._


class RootApplication(exceptionMappers: Seq[ExceptionMapper[_ <: Throwable]],
                      resources: Seq[JaxRsResource]) extends Application {

  override def getSingletons: util.Set[AnyRef] = {
    val singletons = new util.HashSet[AnyRef]()
    singletons.addAll(resources.asJava)
    singletons.addAll(exceptionMappers.asJava)
    singletons
  }
}
