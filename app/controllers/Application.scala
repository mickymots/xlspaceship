package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.json.Json._
import services.{GameService, WSClientService}
import models.{GameRequest, Protocol, Salvo}
import play.api.Logger
import play.api.libs.ws._
import play.api.libs.json._


class Application extends BaseController {
  val gameService: GameService = GameService
  //def gameService: GameService

  /*Handles the incoming salvo from opponent*/
  def acceptSalvo(gameID: String) = Action { implicit request =>

    val json = request.body.asJson.get
    val salvo = Salvo((json \ "salvo").as[Seq[String]])
    Logger.debug("salvo" + salvo)
    val status = gameService.acceptSalvo(gameID, salvo)

    if (!status.gameComplete)
      Ok(toJson(status))
    else
      NotFound(toJson(status))
  }

  /*Handles the current game status request from the user*/
  def showGameStatus(gameID: String) = Action { implicit request =>

    val gameStatus = gameService.getGameStatus(gameID)

    if (gameStatus != null) {
      Ok(toJson(gameStatus)).withHeaders("Access-Control-Allow-Origin" -> "*", "Access-Control-Allow-Methods" -> "GET", "Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept")
    } else
      Ok(toJson(Map("game" -> "game not found"))).withHeaders("Access-Control-Allow-Origin" -> "*", "Access-Control-Allow-Methods" -> "GET", "Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept")
  }

  /* newGame handles the simulation request for a game from user. Response contains player details and game_id.*/
  def newGame = Action { implicit request =>

    val json = request.body.asJson.get

    json.validate[GameRequest] match {
      case s: JsSuccess[GameRequest] => {
        val gameRequest: GameRequest = s.get

        Logger.debug("gameRequest = " + gameRequest)

        Ok(createNewGame(gameRequest)).withHeaders("Access-Control-Allow-Origin" -> "*", "Access-Control-Allow-Methods" -> "GET", "Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept")
      }
      case e: JsError => {
        // error handling flow
        Ok(toJson("Error in request")).withHeaders("Access-Control-Allow-Origin" -> "*", "Access-Control-Allow-Methods" -> "GET", "Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept")

      }
    }
  }

  def preFlight() = Action { implicit request =>
    Ok.withHeaders("Access-Control-Allow-Origin" -> "*", "Access-Control-Allow-Methods" -> "GET", "Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept")
  }

  def preFlightWithParam(gameID: String) = Action { implicit request =>
    Ok.withHeaders("Access-Control-Allow-Origin" -> "*", "Access-Control-Allow-Methods" -> "GET", "Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept")
  }

  /*Delegate method that invokes service calls and processes response from service*/
  private def createNewGame(gameRequest: GameRequest) = {

    val game = gameService createGame gameRequest

    toJson(Map(
      "user_id" -> game.self.id,
      "full_name" -> game.self.name,
      "game_id" -> game.id,
      "starting" -> game.nextTurn.id
    ))
  }

}
