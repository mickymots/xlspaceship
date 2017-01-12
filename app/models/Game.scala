package models

case class Game (id: String, players : Array[Player], complete: Boolean, winner: String, nextTurn: Player)