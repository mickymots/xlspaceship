package models

case class SalvoStatus(hits:Seq[Hit], nextTurn:Player, gameOver: Boolean, gameComplete: Boolean)