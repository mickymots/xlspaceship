package controllers

import play.api.mvc._
import play.api.libs.json.Json._
import services.GameService
import connectors.{GameFormatter}
import play.api.libs.json._
import models.{GameRequest, Protocol}
import play.api.Logger

object Application extends Application {

  val gameService: GameService = GameService
}


trait Application extends BaseController with GameFormatter {

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

    val jsonData = request.body.asJson.get
    val protocol = Protocol( (jsonData \ "spaceship_protocol" \ "hostname" ).as[String], (jsonData \ "spaceship_protocol" \ "port" ).as[Int])
    val gameRequest = GameRequest ( (jsonData \ "user_id" ).as[String],(jsonData \ "full_name" ).as[String], protocol)

    Logger.debug("gameRequest = " + gameRequest)

    Ok(createNewGame(gameRequest))
  }

  private def createNewGame (gameRequest: GameRequest) = {
    toJson(gameService.createGame())
  }
}
