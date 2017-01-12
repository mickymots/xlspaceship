package connectors

import models._ // Combinator syntax
import play.api.libs.json._ // JSON library
import play.api.libs.json.Reads._ // Custom validation helpers
import play.api.libs.functional.syntax._ // Combinator syntax

/*object ProtocolFormatter extends ProtocolFormatter{}*/

trait ProtocolFormatter {

  implicit val gameRequestReads: Reads[Protocol] = (
    (JsPath \ "hostname").read[String] and
      (JsPath \ "port").read[Int]
    )(Protocol.apply _)


}
