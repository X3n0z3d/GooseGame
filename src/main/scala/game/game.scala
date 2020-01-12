package game

import scala.util.Random

case class PlayerPosition(pos: Int, lastPos: Int)

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

      // If i came from a goose (recursive call)... not showind dice roll etc!
      val firstPart: String = if (goose) "" else msg + s"${posOrStart(players(player).lastPos)} to "

      // Effect of the landing box:
      val lastPart: String = newPos match {
        case 6 => // The Bridge
          movePlayer(player, 12)
          s"The Bridge. $player jumps to 12"

        case 63 => // Winning box
          over = true
          s"63. $player Wins!!"

        case n if n > 63 => // Bouncing
          newPos = 63 - newPos % 63
          movePlayer(player, newPos)
          s"63. $player bounces! $player returns to $newPos"

        case n if gooseIndex.contains(n) => // The Goose
          movePlayer(player, newPos)
          val steps = players(player).pos - players(player).lastPos
          s"${players(player).pos}, The Goose. $player moves again and goes to " + move(player, steps, msg, goose = true)

        case normal => // normal spaces without effects
          movePlayer(player, newPos)
          normal.toString
      }

      // The resulting string:
      firstPart + lastPart + prank(player, newPos)
    } else {
      s"No player named $player"
    }
  }

  private def movePlayer(player: String, pos: Int): Unit = {
    players.put(player, PlayerPosition(pos, players(player).pos))
  }

  private def prank(player: String, pos: Int): String = {
    players.filter(e => e._1 != player && e._2.pos == pos)
      .map(p => {
        val otherPlayer = p._1
        val myPrev = players(player).lastPos

        players.put(otherPlayer, PlayerPosition(myPrev, myPrev))
        s". On $pos there is $otherPlayer, who returns to $myPrev"
      }
      ).fold("")(_ + _)
  }

  private def posOrStart(pos: Int) = if (pos==0) "Start" else pos.toString
}