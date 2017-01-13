package controllers

import play.api.mvc._
import play.api.libs.json.Json._
import services.GameService
import connectors.GameFormatter
import play.api.libs.json._
import models.{GameRequest, Protocol, Salvo}
import play.api.Logger
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object Application extends Application {

  val gameService: GameService = GameService
}


trait Application extends BaseController  {

  def gameService: GameService


  def acceptSalvo(gameID: String) = Action { implicit request =>

    val json = request.body.asJson.get

    val salvo = Salvo((json \ "salvo").as[Seq[String]])
    Logger.debug("salvo" + salvo)

    Ok(toJson("salvo received"))
  }

    /*get current game status*/
  def showGameStatus(gameID: String) = Action { implicit request =>

    val gameStatus = gameService.getGame(gameID)

    if(gameStatus != null){
      Ok(toJson(gameStatus))
    }else
    Ok("Game not found")
  }

  /* newGame handles the simulation request for a game from user. Response contains player details and game_id.*/
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

  /*Delegate method that invokes service calls and processes response from service*/
  private def createNewGame (gameRequest: GameRequest) = {

    val game = gameService createGame gameRequest

    toJson(Map(
      "user_id" -> game.self.id,
      "full_name" -> game.self.name,
      "game_id" -> game.id,
      "starting" -> game.nextTurn.id
    ))
  }


}
