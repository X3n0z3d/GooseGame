package cli

import scala.io.StdIn.readLine

object Main extends App {

  val cli = new GooseCli()

  println(
    """THE GOOSE GAME
      | supported commands:
      |   - add player playerName
      |   - move playerName 3, 4        custom dice result
      |   - move playerName             random dice result
      |   - exit                        exit the game
      |""".stripMargin)

  while (!cli.isOver) {
    val s = readLine()
    println(cli.command(s))
  }
}
