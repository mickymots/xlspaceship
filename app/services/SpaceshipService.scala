package services

import models._
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

  /*this class creates spaceships for player*/
  def createSpaceships(board: Board): Array[Spaceship] ={

    var nextID = iDGeneratorService.getNext()
    var coordinates = boardService.allocateCoordinates (board)
    boardService.updateBoard(board, coordinates, "*")
    val winger = Spaceship("spaceship-" + nextID, "Winger", coordinates(0), coordinates(1), true)

    nextID = iDGeneratorService.getNext()
    coordinates = boardService.allocateCoordinates (board)
    boardService.updateBoard(board, coordinates, "*")
    val angle = Spaceship("spaceship-" + nextID, "Angle", coordinates(0), coordinates(1), true)

    nextID = iDGeneratorService.getNext()
    coordinates = boardService.allocateCoordinates (board)
    boardService.updateBoard(board, coordinates, "*")
    val aClass = Spaceship("spaceship-" + nextID, "A-Class", coordinates(0), coordinates(1), true)

    nextID = iDGeneratorService.getNext()
    coordinates = boardService.allocateCoordinates (board)
    boardService.updateBoard(board, coordinates, "*")
    val bClass = Spaceship("spaceship-" + nextID, "B-Class", coordinates(0), coordinates(1), true)

    nextID = iDGeneratorService.getNext()
    coordinates = boardService.allocateCoordinates (board)
    boardService.updateBoard(board, coordinates, "*")
    val sClass = Spaceship("spaceship-" + nextID, "S-Class", coordinates(0), coordinates(1), true)

    Array(winger,angle,aClass,bClass,sClass)

  }

}

