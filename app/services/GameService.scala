package services

import play.api.Logger
import models._
import play.libs.F.None
import sun.net.dns.ResolverConfiguration.Options

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
/**
  * Created by amit.prakash.singh on 12/01/2017.
  */


import play.api.Logger

object GameService extends GameService {
  val iDGeneratorService : IDGeneratorService = IDGeneratorService
  val playerService : PlayerService = PlayerService
}

trait GameService {

  val iDGeneratorService : IDGeneratorService
  val playerService : PlayerService
  val games = scala.collection.mutable.Map[String, Game]()

  def createGame(gameRequest: GameRequest): Game= {

    val player = playerService.createPlayer()
    val opponent = playerService.createOpponent(gameRequest)

    val players = Array(opponent)

    val nextID = iDGeneratorService.getNext()
    val gameID = "Game-" + nextID

    val game = Game(gameID, player, players, false, null, opponent)
    games += (gameID -> game)

    game
  }

  def getGame(gameID : String): GameStatus ={

    var gameStatus : GameStatus =  null
    games get gameID match {
      case s: Option[Game] => {
        val game =  s.get
        gameStatus = GameStatus(game.self, game.opponents(0), game.nextTurn)
        gameStatus
      }
        case e: None[Game] =>{
          Logger.error("Game not found")
          gameStatus
        }
    }
  }


  /*private def createPlayer(gameRequest: GameRequest): Player ={

    if(gameRequest == null){

      val nextID = iDGeneratorService.getNext()

      val playerID = "Player-" + nextID
      val playerName = "Player " + nextID

      Logger.debug("ID = "  + playerID)

      val board = createBoard()
      val spaceships = createSpaceships(board)

      Player(playerID, playerName, spaceships, board)


    }else{
      val board = createBoard()
      Player(gameRequest.userId, gameRequest.userName, null, board)
    }
  }*/

  /**/
 /* private def createBoard(): Board ={

    val nextID = iDGeneratorService.getNext()
    val boardID = "Board-" + nextID


      val rows = ArrayBuffer[Row]()

      for (i <- 0 to 15) {

        val columns = ArrayBuffer[Cell]()

        for (j <- 0 to 15){
          columns += Cell(i,j,".")
        }

        rows += Row(i,columns.toArray)
      }

    Logger.debug("----Board created--" + rows.toString)
    Board(boardID, rows.toArray)
  }*/

  /*/*get random position on board*/
  private def allocateCoordinates(board: Board): Array[Int] = {

    Logger.debug("---Allocate coordinates---")

    var allocated = false
    val coordinates = ArrayBuffer[Int]()
    val randomUtil = new Random()

    while(!allocated) {

      val x = randomUtil.nextInt(16)
      val y = randomUtil.nextInt(16)

      if (board.rows(x).columns(y).status.equals(".")) {
        allocated = true
        //board.rows(x).columns(y).status ="*"
        Logger.info("x,y =" + x + " : " + y)
        coordinates += x
        coordinates += y

      }else{
        Logger.info("x,y allocated - find new one" + x + " : " + y)
      }

    }
    coordinates.toArray
  }*/



  /*/*this class creates spaceships for player*/
  private def createSpaceships(board: Board): Array[Spaceship] ={

    var nextID = iDGeneratorService.getNext()
    var coordinates = allocateCoordinates (board)
    updateBoard(board, coordinates, "*")
    val winger = Spaceship("spaceship-" + nextID, "Winger", coordinates(0), coordinates(1), true)

    nextID = iDGeneratorService.getNext()
    coordinates = allocateCoordinates (board)
    updateBoard(board, coordinates, "*")
    val angle = Spaceship("spaceship-" + nextID, "Angle", coordinates(0), coordinates(1), true)

    nextID = iDGeneratorService.getNext()
    coordinates = allocateCoordinates (board)
    updateBoard(board, coordinates, "*")
    val aClass = Spaceship("spaceship-" + nextID, "A-Class", coordinates(0), coordinates(1), true)

    nextID = iDGeneratorService.getNext()
    coordinates = allocateCoordinates (board)
    updateBoard(board, coordinates, "*")
    val bClass = Spaceship("spaceship-" + nextID, "B-Class", coordinates(0), coordinates(1), true)

    nextID = iDGeneratorService.getNext()
    coordinates = allocateCoordinates (board)
    updateBoard(board, coordinates, "*")
    val sClass = Spaceship("spaceship-" + nextID, "S-Class", coordinates(0), coordinates(1), true)

    Array(winger,angle,aClass,bClass,sClass)

  }*/

}




