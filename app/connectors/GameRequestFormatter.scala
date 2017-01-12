package connectors

import models._
import play.api.libs.json.{Json, Writes} // Combinator syntax

import play.api.libs.json._ // JSON library
import play.api.libs.json.Reads._ // Custom validation helpers
import play.api.libs.functional.syntax._ // Combinator syntax

/*
object GameRequestFormatter extends GameRequestFormatter{}
*/

trait GameRequestFormatter {

  implicit val gameRequestReads: Reads[GameRequest] = (
    (JsPath \ "user_id").read[String] and
      (JsPath \ "full_name").read[String] and
      (JsPath \ "spaceship_protocol").read[Protocol]
    )(GameRequest.apply _)

  implicit val protocolReads: Reads[Protocol] = (
    (JsPath \ "hostname").read[String] and
      (JsPath \ "port").read[Int]
    )(Protocol.apply _)
}
