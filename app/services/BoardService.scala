package services

import models._

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random
/**
  * Created by amit.prakash.singh on 12/01/2017.
  */


import play.api.Logger

object BoardService extends BoardService {
  val iDGeneratorService : IDGeneratorService = IDGeneratorService
}

trait BoardService {

  val iDGeneratorService : IDGeneratorService
  val occupiedList = scala.collection.mutable.ListBuffer[Coordinates]()

  def createBoard(): Board ={

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
  }


  /*get random position on board*/
  def allocateCoordinates(board: Board, spaceshipConfig:  List[Coordinates], range:  List[Coordinates]): ListBuffer[Coordinates] = {
    Logger.debug("---Allocate updated coordinates---")
    var allocated = false
    var coordinatesList: ListBuffer[Coordinates] = null
    do {
      // Step 1. get random coordinates on board
      val randomUtil = new Random()
      val x = randomUtil.nextInt(16)
      val y = randomUtil.nextInt(16)

      println("root:  x = " + x + " y = " + y )

      //Step 2. create updated coordinate list from configuration
      val rangeCoordinatesList = {
        val coordinates = range map (c =>
          Coordinates(c.x + x, c.y + y)
          )
        coordinates.toList
      }

      //Step 3. check if coordinated in range and not occupied?
      var ok = true
      for (c <- rangeCoordinatesList) {
        if (c.x > 15 || c.y > 15 || !board.rows(c.y).columns(c.x).status.equals(".")) {
          ok = false
        }
      }

      if(ok) {
        coordinatesList = {
          val coordinates = spaceshipConfig map (c =>
            Coordinates(c.x + x, c.y + y)
            )
          coordinates.to[ListBuffer]
        }
        allocated = ok
      }

    }while(!allocated)

    coordinatesList
  }

  /*get random position on board*/
  def allocateCoordinates(board: Board): Array[Int] = {

    Logger.debug("---Allocate coordinates---")

    var allocated = false
    val coordinates = ArrayBuffer[Int]()
    val randomUtil = new Random()

    while(!allocated) {

      val x = randomUtil.nextInt(16)
      val y = randomUtil.nextInt(16)

      if (board.rows(x).columns(y).status.equals(".")) {
        allocated = true

        Logger.info("x,y =" + x + " : " + y)
        coordinates += x
        coordinates += y

      }else{
        Logger.info("x,y allocated - find new one" + x + " : " + y)
      }

    }
    coordinates.toArray
  }


  /* update board status */
  /*def updateBoard(board: Board, coordinates: Array[Int], status: String ): Unit ={
    board.rows(coordinates(0)).columns(coordinates(1)).status = status
  }*/

  /* update board status */
  def updateBoard(board: Board, coordinatesList: ListBuffer[Coordinates], status: String ): Unit ={
    for (c <- coordinatesList) {
      board.rows(c.y).columns(c.x).status = status
      occupiedList += c
      }
  }


}

