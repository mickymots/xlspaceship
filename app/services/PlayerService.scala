package services

import models.{Player, Board, Game, Spaceship}
/**
  * Created by amit.prakash.singh on 12/01/2017.
  */


import play.api.Logger

object PlayerService extends PlayerService {

}

trait PlayerService{

  def createPlayer(): Player = {


    Player("Player-1", "Player-1", null, null)
 /*   val opponent = Player("Player-2", "Player-2", null, null)

    val players = Array(player, opponent) //players : Array[Player], complete: Boolean, winner: String, nextTurn: String)
    Game("Game-x", players, false, null, opponent)*/
  }

}

