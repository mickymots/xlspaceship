package models

case class Game (id: String, self: Player, opponents : Array[Player], complete: Boolean, winner: Player, nextTurn: Player)