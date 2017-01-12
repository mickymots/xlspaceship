package models

case class Game (id: String, players : Array[Player], complete: Boolean, winner: Player, nextTurn: Player)