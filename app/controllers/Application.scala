package controllers

import play.api.mvc._
import play.api.libs.json.Json._
import services.GameService
import connectors.{GameFormatter}
import play.api.libs.json._
import models.{GameRequest, Protocol}
import play.api.Logger


import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object Application extends Application {

  val gameService: GameService = GameService
}


trait Application extends BaseController  {

  def gameService: GameService

  def index = Action { implicit request =>
    Ok(currentApi)
  }

  private def currentApi(implicit request: RequestHeader) = {
    toJson(Map(
      "root" -> request.uri
    ))
  }

  def newGame = Action { implicit request =>

    val json = request.body.asJson.get

    json.validate[GameRequest] match {
      case s: JsSuccess[GameRequest] => {
        val gameRequest: GameRequest = s.get

        Logger.debug("gameRequest = " + gameRequest)

        Ok(createNewGame(gameRequest))
      }
      case e: JsError => {
        // error handling flow
        Ok(toJson("Error in request"))
      }
    }
  }

  private def createNewGame (gameRequest: GameRequest) = {
    toJson(gameService.createGame())
  }
}
