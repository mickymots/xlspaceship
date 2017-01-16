package connectors

import models._
import play.api.libs.json.{Json, Writes, _}

import scala.collection.mutable.ArrayBuffer // Combinator syntax

trait SalvoStatusFormatter {


  implicit val salvoStatusWrites = new Writes[SalvoStatus] {


    def writes(salvoStatus: SalvoStatus) : JsObject = {
      if (salvoStatus.nextTurn != null) {
        val hits = salvoStatus.hits
        val game = salvoStatus.gameOver match {
          case true => Json.obj("won" -> salvoStatus.nextTurn.id)
          case false => Json.obj("player_turn" -> salvoStatus.nextTurn.id)
        }

        Json.obj(
          "salvo" -> Json.obj(hits(0).position -> hits(0).status,
            hits(1).position -> hits(1).status,
            hits(2).position -> hits(2).status,
            hits(3).position -> hits(3).status,
            hits(4).position -> hits(4).status),
          "game" -> game
        )
      }else{
        Json.obj("message" -> "Game not found.check GameID.")
      }
    }
  }

}
