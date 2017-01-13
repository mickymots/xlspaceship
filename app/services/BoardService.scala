package services

import models._

import scala.collection.mutable.ArrayBuffer
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
        //board.rows(x).columns(y).status ="*"
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
  def updateBoard(board: Board, coordinates: Array[Int], status: String ): Unit ={

    board.rows(coordinates(0)).columns(coordinates(1)).status = status

  }

}

