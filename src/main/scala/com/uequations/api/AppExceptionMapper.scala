package com.uequations.api

import java.lang.{Exception => JavaException}

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.google.inject.Singleton
import com.typesafe.scalalogging.StrictLogging
import javax.ws.rs.{BadRequestException, NotFoundException, WebApplicationException}
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.ext.{ExceptionMapper, Provider}
import play.api.libs.json.{JsResultException, JsValue, Json}
import akka.http.scaladsl.model.StatusCodes._

import scala.concurrent.TimeoutException


@Provider
@Singleton
class AppExceptionMapper extends ExceptionMapper[JavaException] with StrictLogging {

  def exceptionStatusEntity(exception: JavaException): (Int, JsValue) = {

    def defaultEntity = Json.obj("message" -> exception.getMessage)

    exception match {
      case _: BadRequestException => (BadRequest.intValue, defaultEntity)
      case e: NotFoundException =>
        (
          InternalServerError.intValue,
          Json.obj("message" -> "URI not found"))
      case e: JsonParseException =>
        (
          BadRequest.intValue,
          Json.obj(
            "message" -> "Invalid JSON",
            "details" -> e.getOriginalMessage))
      case e: JsonMappingException =>
        (
          BadRequest.intValue,
          Json.obj(
            "message" -> "Please specify data in JSON format",
            "details" -> e.getMessage))
      case e: JsResultException =>
        val status = if (e.errors.nonEmpty && e.errors.forall { case (_, validationErrors) => validationErrors.nonEmpty })
        // if all of the nested errors are validation-related then generate
        // an error code consistent with that generated for ValidationFailedException
          UnprocessableEntity.intValue
        else
          BadRequest.intValue

        (status, RestResource.entity(e.errors))
      case e: WebApplicationException =>
        val entity = Option(Status.fromStatusCode(e.getResponse.getStatus)) match {
          case None =>
            Json.obj("message" -> e.getMessage)
          case Some(status) =>
            Json.obj("message" -> status.getReasonPhrase)
        }
        (e.getResponse.getStatus(), entity)

      case _: TimeoutException => (ServiceUnavailable.intValue, defaultEntity)
      case _: IllegalArgumentException => (UnprocessableEntity.intValue, defaultEntity)

      case _ =>
        (InternalServerError.intValue, defaultEntity)
    }
  }

  def exceptionToResponse(exception: JavaException): Response = {
    exception match {
      case e: NotFoundException =>

      case e: WebApplicationException =>

      case _ =>
    }

    val (statusCode, entity) = exceptionStatusEntity(e)
  }

  override def toResponse(exception: JavaException): Response = {
    exception match {
      case e => exceptionToResponse(e)
    }
  }
}
