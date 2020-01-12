import cli.GooseCli
import org.scalatest.FunSuite


class TestGoose extends FunSuite {

  test("Add player") {
    val gameCli = new GooseCli()

    assert(gameCli.command("add player Pippo") === "players: Pippo")
    assert(gameCli.command("add player Pluto") === "players: Pippo, Pluto")
    assert(gameCli.command("add player Pippo") === "Pippo: already existing player")
  }

  test("Move a player") {
    val gameCli = new GooseCli()

    gameCli.command("add player Pippo")
    gameCli.command("add player Pluto")
    assert(gameCli.command("move Pippo 4, 3") === "Pippo rolls 4, 3. Pippo moves from Start to 7")
    assert(gameCli.command("move Pluto 2, 2") === "Pluto rolls 2, 2. Pluto moves from Start to 4")
    assert(gameCli.command("move Pippo 1, 3") === "Pippo rolls 1, 3. Pippo moves from 7 to 11")
    assert(gameCli.command("move Pluto 2,4") === "Pluto rolls 2, 4. Pluto moves from 4 to 10")

    // Wrong input
    assert(gameCli.command("move Pippo 7, 3") === "dice value must be between 1 and 6!")
    assert(gameCli.command("move Pippo 3") === "unknown/invalid command")
    assert(gameCli.command("move Giovanni 3, 4") === "No player named Giovanni")
  }

  test("Win") {
    val gameCli = pippoAt60()
    assert(gameCli.command("move Pippo 1, 2") === "Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!")
  }

  test("Bouncing on 63") {
    val gameCli = pippoAt60()
    assert(gameCli.command("move Pippo 3, 2") === "Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61")
    assert(gameCli.command("move Pippo 5, 6") === "Pippo rolls 5, 6. Pippo moves from 61 to 63. Pippo bounces! Pippo returns to 54")
  }

  test("The game throws the dice") {
    val gameCli = new GooseCli()
    gameCli.command("add player Pippo")

    assert(gameCli.command("move Pippo").startsWith("Pippo rolls")) //... unpredictable!
  }

  test("Space 6 is the bridge") {
    val gameCli = new GooseCli()
    gameCli.command("add player Pippo")

    gameCli.command("move Pippo 3, 1")
    assert(gameCli.command("move Pippo 1, 1") === "Pippo rolls 1, 1. Pippo moves from 4 to The Bridge. Pippo jumps to 12")
  }

  test("the goose") {
    val gameCli = new GooseCli()
    gameCli.command("add player Pippo")
    gameCli.command("move Pippo 1, 2")
    assert(gameCli.command("move Pippo 1, 1") === "Pippo rolls 1, 1. Pippo moves from 3 to 5, The Goose. Pippo moves again and goes to 7")

    gameCli.command("move Pippo 1, 2") // 10
    // Multiple jumps
    assert(gameCli.command("move Pippo 2, 2") === "Pippo rolls 2, 2. Pippo moves from 10 to 14, The Goose. " +
      "Pippo moves again and goes to 18, The Goose. Pippo moves again and goes to 22")

    gameCli.command("add player Pluto")
    assert(gameCli.command("move Pluto 3, 6") === "Pluto rolls 3, 6. Pluto moves from Start to 9, The Goose. " +
      "Pluto moves again and goes to 18, The Goose. Pluto moves again and goes to 27, The Goose. Pluto moves again and goes to 36")
  }

  test("Prank") {
    val gameCli = new GooseCli()
    gameCli.command("add player Pippo")
    gameCli.command("add player Pluto")

    gameCli.command("move Pippo 6, 6")
    gameCli.command("move Pippo 1, 2") // 15

    gameCli.command("move Pluto 6, 6")
    gameCli.command("move Pluto 3, 2") // 17

    assert(gameCli.command("move Pippo 1, 1") === "Pippo rolls 1, 1. Pippo moves from 15 to 17. On 17 there is Pluto, who returns to 15")

    // Testing effectiveness of position changing (WITH DUEL!!)
    assert(gameCli.command("move Pluto 1, 1") === "Pluto rolls 1, 1. Pluto moves from 15 to 17. On 17 there is Pippo, who returns to 15")
  }


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
