package services

import play.api.Logger
import models._
import play.libs.F.None
import sun.net.dns.ResolverConfiguration.Options

import scala.collection.mutable.ArrayBuffer

import scala.util.Try

import play.api.Logger

object GameService extends GameService {
  val iDGeneratorService : IDGeneratorService = IDGeneratorService
  val playerService : PlayerService = PlayerService
  val boardService : BoardService = BoardService
}

trait GameService {

  val iDGeneratorService : IDGeneratorService
  val playerService : PlayerService
  val boardService : BoardService

  val games = scala.collection.mutable.Map[String, Game]()

  /*Creates a new game*/
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


  /*finds a game for DB and returns its status*/
  def getGameStatus(gameID : String): GameStatus ={

    var gameStatus : GameStatus =  null
    games get gameID match {
      case s: Option[Game] => {
        val game =  s.get
        gameStatus = GameStatus(game.self, game.opponents(0), game.nextTurn)
        gameStatus
      }
    }
  }

  /*finds a game for repos and returns its status*/
  def acceptSalvo(gameID : String, salvo: Salvo): SalvoStatus ={
    Logger.debug("\n\n----- Accept Salvo --------\n\n")
    games get gameID match {
      case s: Option[Game] => {

        val game = s.get
        val hits = ArrayBuffer[Hit]()

        if(!game.complete){

          val self = game.self
          val spaceships = self.spaceships
          for (position <- salvo.hits) {
            val hit = Hit(position, "miss")
            val coordinates = getCoordinate(position)

            var success = false
            for (spaceship <- spaceships) {
              if(!success)
                success = processSalvo(self.board, hit, spaceship, coordinates)
            }
            hits += hit
          }

          var gameOver = true

          for (spaceship <- spaceships) {
            if(spaceship.status.equals("active") || spaceship.status.equals("hit"))
              gameOver = false
          }
          if(gameOver)
            game.complete = true

          SalvoStatus(hits, self, gameOver, false)
        }else{
          for (position <- salvo.hits) {
            val hit = Hit(position, "miss")
            hits += hit
          }
          SalvoStatus(hits, game.self, game.complete, game.complete )
        }



      }
    }
  }

  //procesSalvo
  private def processSalvo(board: Board, hit: Hit, spaceship: Spaceship,coordinates :Array[Int] ): Boolean ={
    Logger.debug("----- processSalvo --------")
    Logger.debug("space-x" + spaceship.x )
    Logger.debug("space-y" + spaceship.y )

    var success = false

    if(spaceship.x == coordinates(0) && spaceship.y == coordinates(1)) {
      Logger.debug("spaceship hit = " + spaceship)
      success = true
      spaceship.status match {
        case "active" =>{
          hit.status = "hit"
          spaceship.status = "hit"
          boardService.updateBoard(board,coordinates, "X")
        }
        case "hit" =>{
          hit.status = "kill"
          spaceship.status = "kill"
          //boardService.updateBoard(board,coordinates, "X")
        }
        case "kill" =>{
          hit.status = "miss"
        }
        case _ =>{
          Logger.debug("no match found for spaceship status =" + spaceship.status)
        }
      }
    }else {
      Logger.debug("spaceship hit = " + spaceship)
      hit.status = "miss"
      boardService.updateBoard(board,coordinates, "-")
    }
    success
  }

  //String to coordinate array conversion
  private def getCoordinate (position: String): Array[Int] ={

    Logger.debug("----- getCoordinate --------")
    val arr = position.split("x")

    val x = decode(arr(0))
    Logger.debug("x:" + x)
    val y =  decode(arr(1))
    Logger.debug("y:" + y)

    Array(x,y)
  }

  //String to Int conversion
  //def toInt( s: String ) = Try(s.toInt).toOption

  def decode(str: String): Int ={

      var code = Try(str.toInt).getOrElse(-1)
      if(code > -1){
        code
      }else
        {
          code = str match {
            case "A" => 10
            case "B" => 11
            case "C" => 12
            case "D" => 13
            case "E" => 14
            case "F" => 15
           }
        }
    code
    }
}




