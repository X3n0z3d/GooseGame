package cli

object MainTest extends App {

  val cli = new GooseCli()
  println(cli.command("add player Pippo"))
  println(cli.command("add player Pluto"))
  println(cli.command("add player Gigi"))
//
//  println(cli.command("move Pluto 1, 1"))
//
//  println(cli.command("move Pippo 3, 3"))
//  println(cli.command("move Pippo 2, 3"))
//
//  println(cli.command("move Pluto 1, 1"))
//
//  println(cli.command("add player Gigi"))
//  println(cli.command("move Gigi 1, 2"))
//  println(cli.command("move Gigi 1, 1"))
//
//  println(cli.command("add player Multiple"))
//  println(cli.command("move Multiple 5, 5"))
//  println(cli.command("move Multiple 2, 2"))
//  println(cli.command("move Multiple 6, 6"))
//  println(cli.command("move Multiple 6, 6"))
//  println(cli.command("move Multiple 6, 6"))
//  println(cli.command("move Multiple 2, 5"))
//  println(cli.command("move Multiple 5, 5"))
//  println(cli.command("move Multiple 5, 5"))
//  println(cli.command("move Multiple 1, 2"))


  // prank
//  println(cli.command("move Pluto 1, 1"))
//  println(cli.command("move Pluto 1, 2"))
//  println(cli.command("move Pippo 1, 1"))
//  println(cli.command("move Pippo 1, 2"))

//  println(cli.command("move Pluto 3, 6"))
//  println(cli.command("move Pluto 3, 3"))
//  println(cli.command("move Pippo 6, 6"))
//  println(cli.command("move Pippo 2, 4"))

  cli.command("move Pippo 1, 2")
  println(cli.command("move Pippo 1, 1"))


  def pippoAt60(): GooseCli = {
    val gameCli = new GooseCli()
    gameCli.command("add player Pippo")

    gameCli.command("move Pippo 6, 6")
    gameCli.command("move Pippo 6, 6")
    gameCli.command("move Pippo 6, 6")
    gameCli.command("move Pippo 6, 6")
    gameCli.command("move Pippo 6, 6")
    gameCli
  }
}
