package services

import models._
/**
  * Created by amit.prakash.singh on 12/01/2017.
  */


import play.api.Logger

object PlayerService extends PlayerService {
  val iDGeneratorService : IDGeneratorService = IDGeneratorService
  val boardService : BoardService = BoardService
  val spaceshipService : SpaceshipService = SpaceshipService
}

trait PlayerService {

  val iDGeneratorService : IDGeneratorService
  val boardService : BoardService
  val spaceshipService : SpaceshipService


  def createPlayer(): Player ={
      val nextID = iDGeneratorService.getNext()

      val playerID = "Player-" + nextID
      val playerName = "Player " + nextID

      Logger.debug("ID = "  + playerID)

      val board = boardService.createBoard()
      val spaceships = spaceshipService.createSpaceships(board)

      Player(playerID, playerName, spaceships, board)

  }

  def createOpponent(gameRequest: GameRequest): Player ={
      val board = boardService.createBoard()
      Player(gameRequest.userId, gameRequest.userName, null, board)
  }

}

