package models

case class Game (id: String, self: Player, opponents : Array[Player], var complete: Boolean, winner: Player, nextTurn: Player)