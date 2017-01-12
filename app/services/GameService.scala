package services


import models.{Player, Board, Game, Spaceship}
/**
  * Created by amit.prakash.singh on 12/01/2017.
  */


import play.api.Logger

object GameService extends GameService {

}

trait GameService{

  def createGame(): Game ={


    val player = Player ("Player-1", "Player-1", null, null)
    val opponent = Player("Player-2","Player-2", null, null)

    val players = Array(player, opponent)//players : Array[Player], complete: Boolean, winner: String, nextTurn: String)
    Game("Game-x", players, false, null, opponent )

    //val game = Game((jsonData \ "context").as[JsValue], (jsonData \ "messageType").as[String], (jsonData \ "text").as[String], person, (jsonData \ "watsonVariables").as[JsValue])

  /*  vaService converse watsonPayload
    val jsonRet = Json.toJson(watsonPayload)


    // Return the response from the VAService
    Ok(s"$jsonRet").withHeaders("Content-Security-Policy" -> "http://www.youtube.com", "Access-Control-Allow-Origin" -> "*", "Access-Control-Allow-Methods" -> "POST", "Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept")
*/

  }

  /*def anonymizeUserResponse(watsonPayload: WatsonPayload) : WatsonPayload = {
    Logger.info("\n------ anonymizeUserResponse Start ----------\n")

    sanitizeEmployerInfoInUserResponse(watsonPayload)
    sanitizeDOBInUserResponse(watsonPayload)
    Logger.info("\n------ anonymizeUserResponse End ----------\n")
    watsonPayload
  }


  def sanitizeEmployerInfoInUserResponse(watsonPayload: WatsonPayload) : WatsonPayload = {
    var userResponse  = watsonPayload.text
    val person = watsonPayload.person

    Logger.info("userResponse = " + person.employer.toLowerCase())

    //check employer in user response and replace with empty string
    if(person.employer.length >0 && userResponse.toLowerCase().indexOf(person.employer.toLowerCase()) > -1){
      Logger.info("Found employer info in response. Replaced with empty string")
      userResponse = userResponse.toLowerCase().replaceAll(person.employer.toLowerCase(), "")
    }

    watsonPayload.text = userResponse
    watsonPayload
  }

  def sanitizeDOBInUserResponse(watsonPayload: WatsonPayload) : WatsonPayload = {

    var userResponse  = watsonPayload.text

    //check date of birth in user response
    val dobSlashNumberFormat = Pattern.compile("((\\s*)(\\d{2})(\\s*\\/\\s*)(\\d{2})(\\s*\\/\\s*\\d{4}))")

    //TODO : Add additional popular DOB formats and check them in user response

    val m = dobSlashNumberFormat.matcher(userResponse)
    //val b = m.matches()

    if (m.find()) {
      Logger.info("\n DOB Found \n")
      Logger.info("DOB = " + m.group(1))
      userResponse = userResponse.replaceAll(m.group(1), "")
    }else {
      Logger.info("no dob found")
    }

    watsonPayload.text = userResponse
    watsonPayload
  }*/
}

