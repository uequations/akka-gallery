package com.uequations.api

import play.api.libs.json.{JsPath, JsValue, Json, JsonValidationError}

object RestResource {

  def entity(err: Seq[(JsPath, Seq[JsonValidationError])]): JsValue = {
    val errors = err.map {
      case (path, errs) => Json.obj("path" -> path.toString(), "errors" -> errs.map(_.message).distinct)
    }
    Json.obj(
      "message" -> "Invalid JSON",
      "details" -> errors
    )
  }
}
