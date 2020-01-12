package game

import scala.util.Random

case class PlayerPosition(pos: Int, lastPos: Int)

object Space extends Enumeration {
  type Space = Value
  val Goose, Bridge, Normal, Win = Value
}

class GooseGame {

  // Players and their positions
  private val players = new scala.collection.mutable.HashMap[String, PlayerPosition]()
  private val gooseIndex = Seq(5, 9, 14, 18, 23, 27)

  // the game is over?
  var over = false

  // Adding player to game
  def addPlayer(player: String): String = {
    if (!players.contains(player)) {
      players.put(player, PlayerPosition(0, 0))
      s"players: ${players.keys.reduce(_ + ", " + _)}"
    } else s"$player: already existing player"
  }

  // Moving with custom dice values
  def move(player: String, d1: Int, d2: Int): String = {
    val msg = s"$player rolls $d1, $d2. $player moves from "
    move(player, d1+d2, msg)
  }

  // Move with dice throwing
  def move(player: String): String = {
    move(player, Random.nextInt(6)+1, Random.nextInt(6)+1)
  }

  private def move(player: String, steps: Int, msg: String, goose: Boolean = false): String = {
    if (players.contains(player)) {
      // Update last position and computing new one
      players.put(player, PlayerPosition(players(player).pos, players(player).pos))
      var newPos: Int = players(player).pos + steps

      // If i came from a goose (recursive call)... the output changes!
      val firstPart: String = if (goose) "" else msg + s"${posOrStart(players(player).lastPos)} to "

      val lastPart: String = newPos match {
        // The Bridge
        case 6 =>
          players.put(player, PlayerPosition(12, players(player).pos))
          s"The Bridge. $player jumps to 12"

        // Bouncing
        case n if n > 63 =>
          newPos = 63 - newPos % 63
          players.put(player, PlayerPosition(newPos, players(player).pos))
          s"63. $player bounces! $player returns to $newPos"
        // The Goose
        case n if gooseIndex.contains(n) =>
          players.put(player, PlayerPosition(newPos, players(player).pos))
          val steps = players(player).pos - players(player).lastPos
          s"${players(player).pos}, The Goose. $player moves again and goes to " + move(player, steps, msg, true)
        case other =>
          players.put(player, PlayerPosition(newPos, players(player).pos))
          other match {
            case 63 =>
              over = true
              s"63. $player Wins!!"

            case normal => normal.toString
          }

    }
      firstPart + lastPart + prank(player, newPos)
    } else {
      s"No player named $player"
    }
  }

  private def prank(player: String, pos: Int): String = {
    players.filter(e => e._1 != player && e._2.pos == pos)
      .map(p => {
        val otherPlayer = p._1
        val myPrev = players(player).lastPos

        players.put(otherPlayer, PlayerPosition(myPrev, myPrev))
        s". On $pos there is $otherPlayer, who returns to $myPrev"
      }
      ).fold("")(_ + "" + _)
  }

  private def posOrStart(pos: Int) = if (pos==0) "Start" else pos.toString
}