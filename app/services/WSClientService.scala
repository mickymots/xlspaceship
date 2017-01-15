package services

import models._

import scala.concurrent._

import ExecutionContext.Implicits.global
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.{Failure, Random, Success}
import play.api.libs._
import play.api.libs.ws._
import javax.inject.Inject
import javax.inject.Inject
import scala.concurrent.Future

import play.api.Logger
import play.api.libs.json._

/**
  * Created by amit.prakash.singh on 12/01/2017.
  */

object WSClientService extends WSClientService{
}

trait  WSClientService  {

   def fireSalvo(salvo: Salvo, ws: WSClient): String ={

    val url = "http://www.mocky.io/v2/587ac1251100006716d39466"

    val request: WSRequest= ws.url(url)

    val data = Json.obj("salvo" -> salvo.hits)
    println(data)

    val futureResponse: Future[WSResponse] = request.put(data)

     //TODO: Map response properly
    val futureResult: Future[String] = futureResponse.map {
      response =>
        (response.json).as[String]
    }

    var response : String = null
    futureResult onComplete {
      case Success(result) => {
        println(result)
        response = result
      }
      case Failure(t) => println("An error has occured: " + t.getMessage)
    }
    response
  }



}
