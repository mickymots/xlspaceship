package services

import play.api.Logger
import models._

import sun.net.dns.ResolverConfiguration.Options

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.{Random, Try}
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

        if(s != None) {
          val game = s.get
          gameStatus = GameStatus(game.self, game.opponents(0), game.nextTurn)
          gameStatus
        }else{
            Logger.debug("Game not found")
            gameStatus
        }
      }

    }
  }

  /*finds a game for repos and returns its status*/
  def acceptSalvo(gameID : String, salvo: Salvo): SalvoStatus = {
    Logger.debug("\n\n----- Accept Salvo --------\n\n")

    //Step 1. Load Game
    games get gameID match {
      case s: Option[Game] => {

        val game = s.get
        val hits = ArrayBuffer[Hit]()
        //Step 2. if game not complete process salvo , BW
        if (!game.complete) {
          //Step 2.1 - load spaceships
          val spaceships = game.self.spaceships

          //Step 2.2 loop all salvo hits
          for (position <- salvo.hits) {

            //Step 2.2.1 create empty hit with status miss and get coordinates
            val hit = Hit(position, "miss")
            val coordinates = getCoordinate(position)

            var success = false
            //Step 2.2.2 loop through spaceships to record hit, if any
            for (spaceship <- spaceships) {
              if (!success)
                success = processSalvo(game.self.board, hit, spaceship, coordinates)
            }

            //Step 2.2.3 add hit to array
            hits += hit
          }

          //Step 2.3 Check game status
          var gameOver = true
          for (spaceship <- spaceships) {
            if (spaceship.active)
              gameOver = false
          }

          if (gameOver)
            game.complete = gameOver

          SalvoStatus(hits, game.self, gameOver, false)

        } else {
          //Step 3.create all miss salvo with winner
          for (position <- salvo.hits) {
            val hit = Hit(position, "miss")
            hits += hit
          }
          SalvoStatus(hits, game.self, game.complete, game.complete)
        }
      }
      //case None => println("game not found")
    }
  }

  // loop through spaceship parts and record hits and also update board
  private def processSalvo(board: Board, hit: Hit, spaceship: XLSpaceship,coordinates :Array[Int] ): Boolean ={
    Logger.debug("----- processSalvo on XLSpaceship --------")
    Logger.debug("hit(x:y)" + hit.position )
    var success = false

    for(part <- spaceship.parts){
      //if hit not processed
      if(!success) {
        if (part.x == coordinates(0) && part.y == coordinates(1)) {
          Logger.debug("spaceship hit = " + spaceship + "X:y = " + hit.position)
          success = true
          hit.status = "hit"
        }
      }
    }

    //if hit was success remove part from spaceship and update spaceship status
    if(success){
      spaceship.parts -= Coordinates(coordinates(0), coordinates(1))
      val removeParts = ListBuffer(Coordinates(coordinates(0), coordinates(1)))
      boardService.updateBoard(board,removeParts, "X")
    }

    //if spaceship parts empty then update status and it was hit then
    if(success && spaceship.parts.length == 0) {
      spaceship.active = false
      hit.status = "kill"
    }
    success
  }

  //Really dumb salvo creator
  def createSalvo(): Salvo ={
    val randomUtil = new Random()

    var hits = ArrayBuffer[String]()
    for(i <- 0 to 4 ){

      val x = randomUtil.nextInt(16)
      val y = randomUtil.nextInt(16)
      val pos = x + "x" + y
      hits += pos
    }
    Salvo(hits)
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

  private def decode(str: String): Int ={

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




