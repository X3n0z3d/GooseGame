package cli

import game._

class GooseCli() {
  private val myGame = new GooseGame()

  def command(cmd: String): String = {
    cmd.split("[ ,]+").toList match {
      case List("add", "player", name) => myGame.addPlayer(name)
      case List("move", player, d1, d2) if d1.forall(_.isDigit) && d2.forall(_.isDigit) => {
        if (d1.toInt>0 && d1.toInt<7 && d2.toInt>0 && d2.toInt<7)
          myGame.move(player, d1.toInt, d2.toInt)
        else
          "dice value must be between 1 and 6!"
      }
      case List("move", player) => myGame.move(player)
      case List("exit") => myGame.over = true; "exiting the game, bye!"
      case _ => "unknown/invalid command"
    }
  }

  def isOver = myGame.over
}
