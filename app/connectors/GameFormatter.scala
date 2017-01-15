package connectors

import models._
import play.api.libs.json.{JsPath, Json, Reads, Writes}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


import scala.collection.mutable.ArrayBuffer // Combinator syntax

trait GameFormatter {

  //implicit val personReads = Json.reads[Person]

//  /*implicit val personReads: Reads[Person] = (
//    (JsPath \ "name").read[String] and
//      (JsPath \ "dateOfBirth").read[String] and
//      (JsPath \ "employer").read[String] and
//      (JsPath \ "income").read[String] and
//      (JsPath \ "taxableBenefit").read[String] and
//      (JsPath \ "taxCode").read[String] and
//      (JsPath \ "nino").read[String]
//    )(Person.apply _)*/


  implicit val gameWrites = new Writes[Game] {
    def writes(game: Game) = Json.obj(
      "game_id" -> game.id
    )
  }

  implicit val gameStatusWrites = new Writes[GameStatus] {

    def writes(gameStatus: GameStatus) : JsObject = {

      var rows = gameStatus.self.board.rows
      val selfBoard = ArrayBuffer[String]()

      //create matrix of self board
      for(row <- rows){
        val columns = row.columns
        var rowBuilder = ""
        for(col <- columns){
          rowBuilder += col.status
        }
        selfBoard += rowBuilder
      }

      rows = gameStatus.opponent.board.rows
      val opponentBoard = ArrayBuffer[String]()
      //create matrix of opponent board
      for(row <- rows){
        val columns = row.columns
        var rowBuilder = ""
        for(col <- columns){
          rowBuilder += col.status
        }
        opponentBoard += rowBuilder
      }


      Json.obj(
      "self" -> Json.obj("user_id" -> gameStatus.self.id,
                          "board" -> selfBoard),
      "opponent" ->   Json.obj("user_id" -> gameStatus.opponent.id,
        "board" -> opponentBoard),
        "game" -> Json.obj("player_turn" -> gameStatus.nextTurn.id)
      )
    }
  }

}
