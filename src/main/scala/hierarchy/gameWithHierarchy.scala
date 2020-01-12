package hierarchy

import hierarchy.Space.Space

import scala.util.Random

case class PlayerPosition(pos: Int, lastPos: Int)

object Space extends Enumeration {
  type Space = Value
  val Goose, Bridge, Normal, Win = Value
}

class GooseGameWithHierarchy {
  // Boxes hierarchy
  trait Box {
    def effect(player: String, msg: String): String = players(player).pos.toString
  }
  object SimpleBox extends Box
  object BridgeBox extends Box {
    override def effect(player: String, msg: String): String = {
      players.put(player, PlayerPosition(12, players(player).pos))
      s"The Bridge. $player jumps to 12"
    }
  }
  object GooseBox extends Box {
    override def effect(player: String, msg: String): String = {
      val steps = players(player).pos-players(player).lastPos
      s"${players(player).pos}, The Goose. $player moves again and goes to " + move(player, steps, msg, true)
    }
  }
  object WinBox extends Box {
    override def effect(player: String, msg: String): String = {
      over = true
      s"63. $player Wins!!"
    }
  }

  // Players and their positions
  private val players = new scala.collection.mutable.HashMap[String, PlayerPosition]();
  private val gooseIndex = Seq(5, 9, 14, 18, 23, 27)
  // To generate the boxes
  private def spaceFiller(i: Int): Box = i match {
    case 6 => BridgeBox
    case 63 => WinBox
    case _ if gooseIndex.contains(i) => GooseBox
    case _ => SimpleBox
  }

  // Generation of the game's board
  private val board: Map[Int, Box] =  (for (i <- 0 to 63) yield (i -> spaceFiller(i))).toMap

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

  /* Prank moves players to my last position
   */
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

  private def move(player: String, steps: Int, msg: String, goose: Boolean = false): String = {
    if (players.contains(player)) {
      // Update last position and computing new one
      players.put(player, PlayerPosition(players(player).pos, players(player).pos))
      var newPos = players(player).pos + steps

      // Bouncing:
      if (newPos > 63) {
        newPos = 63 - newPos % 63
        players.put(player, PlayerPosition(newPos, players(player).pos))
        msg + s"${players(player).lastPos} to 63. $player bounces! $player returns to ${newPos}"
      } else {
        // Update current position
        players.put(player, PlayerPosition(newPos, players(player).pos))

        (if (goose) "" else msg + s"${posOrStart(players(player).lastPos)} to ") + board(newPos).effect(player, msg) + prank(player, newPos)
      }
    } else {
      s"No player named $player"
    }
  }

  private def posOrStart(pos: Int) = if (pos==0) "Start" else pos.toString
}