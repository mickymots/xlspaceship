package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.json.Json._
import services.{GameService, WSClientService}
import models.{GameRequest, Protocol, Salvo}
import play.api.Logger
import play.api.libs.ws._
import play.api.libs.json._
import play.api.Play
import play.api.Play.current
import play.api.libs.ws._

/*object WSClientController extends  WSClientController{
  val gameService: GameService = GameService
  val ws: WSClientService = WSClientService
}*/

class  WSClientController  @Inject() (ws: WSClient) extends Controller{

  val gameService: GameService = GameService
  val wsClientService :  WSClientService = WSClientService

    /*handles fire salvo request from user*/
  def fireSalvo(gameID: String) = Action {

    //Step 1. get salvo from gameService
    val salvo = gameService.createSalvo()
    //Step 2. Call WS Client Service and fire salvo
    val response = wsClientService.fireSalvo(salvo, ws)
    Ok(toJson("Shots fired = " + response))
  }
}
