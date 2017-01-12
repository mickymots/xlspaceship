package controllers

import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.json.Json._
import services.GameService

import connectors.{GameFormatter}

object Application extends Application {

  val gameService: GameService = GameService

}


trait Application extends Controller  with GameFormatter {

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

    Ok(createNewGame)

  }

  private def createNewGame(implicit request: RequestHeader) = {
      toJson(gameService.createGame())
  }
}
