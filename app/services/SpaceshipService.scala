package services

import models._
import play._
import play.api.libs.json.{Json, Writes, _}
import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
/**
  * Created by amit.prakash.singh on 12/01/2017.
  */


import play.api.Logger

object SpaceshipService extends SpaceshipService {
  val iDGeneratorService : IDGeneratorService = IDGeneratorService
  val boardService : BoardService = BoardService
}

trait SpaceshipService {

  val iDGeneratorService : IDGeneratorService
  val boardService : BoardService


  /**/
  def loadSpaceShipConfiguration(spaceshipType: String): List[Coordinates] ={

    Logger.debug(" \n loadSpaceShipConfiguration --> " + spaceshipType)

      //val wingers = new Array[Coordinates](20)
      val coordinatesList: List[Coordinates] = {
      val coordinates = Play.application.configuration.getConfigList(spaceshipType) map { p =>
        Coordinates(p.getInt("x"), p.getInt("y"))
      }
        coordinates.toList
      }

      val it = coordinatesList iterator

      while (it.hasNext) {
        val config = it.next()
        println(config.x + ":" + config.y)

      }
    coordinatesList

  }

  /*this class creates spaceships for player*/
  def createSpaceships(board: Board): Array[XLSpaceship] ={

    //
    var range = loadSpaceShipConfiguration("range")
    //Step 1. Load config of spaceship
    var configCoordinates = loadSpaceShipConfiguration("winger")
    //Step 2. Get unused coordinates
    var allocatedCoordinates = boardService.allocateCoordinates(board,configCoordinates, range)
   //Step 3. updated board with config
    boardService.updateBoard(board, allocatedCoordinates, "*")

    //Step 4. create spaceship
    var nextID = iDGeneratorService.getNext()
    val winger = XLSpaceship("Winger", allocatedCoordinates, true)


    //Step 1. Load config of spaceship
    configCoordinates = loadSpaceShipConfiguration("angle")
    //Step 2. Get unused coordinates
    allocatedCoordinates = boardService.allocateCoordinates(board,configCoordinates,range)
    //Step 3. updated board with config
    boardService.updateBoard(board, allocatedCoordinates, "*")

    //Step 4. create spaceship
    val angle = XLSpaceship("Angle", allocatedCoordinates, true)


    //Step 1. Load config of spaceship
    configCoordinates = loadSpaceShipConfiguration("aClass")
    //Step 2. Get unused coordinates
    allocatedCoordinates = boardService.allocateCoordinates(board,configCoordinates, range)
    //Step 3. updated board with config
    boardService.updateBoard(board, allocatedCoordinates, "*")

    //Step 4. create spaceship
    val aClass = XLSpaceship("A-Class", allocatedCoordinates, true)

    //Step 1. Load config of spaceship
    configCoordinates = loadSpaceShipConfiguration("bClass")
    //Step 2. Get unused coordinates
    allocatedCoordinates = boardService.allocateCoordinates(board,configCoordinates, range)
    //Step 3. updated board with config
    boardService.updateBoard(board, allocatedCoordinates, "*")

    //Step 4. create spaceship
    val bClass = XLSpaceship("B-Class", allocatedCoordinates, true)


    //Step 1. Load config of spaceship
    configCoordinates = loadSpaceShipConfiguration("sClass")
    //Step 2. Get unused coordinates
    allocatedCoordinates = boardService.allocateCoordinates(board,configCoordinates, range)
    //Step 3. updated board with config
    boardService.updateBoard(board, allocatedCoordinates, "*")

    //Step 4. create spaceship
    val sClass = XLSpaceship("S-Class", allocatedCoordinates, true)


    Array(winger,angle,aClass,bClass,sClass)

  }

}

/*
* nextID = iDGeneratorService.getNext()
    var coordinates = boardService.allocateCoordinates (board)
    //boardService.updateBoard(board, coordinates, "*")
    val winger = Spaceship("spaceship-" + nextID, "Winger", coordinates(0), coordinates(1), "active")

    nextID = iDGeneratorService.getNext()
    coordinates = boardService.allocateCoordinates (board)
   // boardService.updateBoard(board, coordinates, "*")
    val angle = Spaceship("spaceship-" + nextID, "Angle", coordinates(0), coordinates(1), "active")

    nextID = iDGeneratorService.getNext()
    coordinates = boardService.allocateCoordinates (board)
    //boardService.updateBoard(board, coordinates, "*")
    val aClass = Spaceship("spaceship-" + nextID, "A-Class", coordinates(0), coordinates(1), "active")

    nextID = iDGeneratorService.getNext()
    coordinates = boardService.allocateCoordinates (board)
   // boardService.updateBoard(board, coordinates, "*")
    val bClass = Spaceship("spaceship-" + nextID, "B-Class", coordinates(0), coordinates(1), "active")

    nextID = iDGeneratorService.getNext()
    coordinates = boardService.allocateCoordinates (board)
    //boardService.updateBoard(board, coordinates, "*")
    val sClass = Spaceship("spaceship-" + nextID, "S-Class", coordinates(0), coordinates(1), "active")

* */
