package connectors

import models._
import play.api.libs.json.{JsPath, Json, Reads, Writes} // Combinator syntax



import play.api.libs.json.Reads._ // Custom validation helpers
import play.api.libs.functional.syntax._ // Combinator syntax

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

  //implicit writes to convert a WatsonPayLoad to JSON
  implicit val gameWrites = new Writes[Game] {
    def writes(game: Game) = Json.obj(
      "game_id" -> game.id
    )
  }

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
