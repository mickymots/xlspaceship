package services

import models.{Player, Board, Game, Spaceship}
/**
  * Created by amit.prakash.singh on 12/01/2017.
  */


object IDGeneratorService extends IDGeneratorService {
}

trait IDGeneratorService{

  var x : Int = 0

  def getNext(): Int = {
    this.synchronized {
      x += 1
      x
    }
  }

}

