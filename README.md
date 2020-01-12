# The Goose Game

Goose game kata
Developed with IntelliJ IDEA

### Playing

Run "Main" file in IntellJ, or "sbt run" with a terminal in the root folder of the proj.

## Running the tests

**In IntelliJ:** right click on the project and "run scalatest"

**In sbt:** sbt test from the root folder of the project

## Note

I've implemented two version of the game, the first one is **hierarchy version** (hierarchy package), where the spaces are saved in a map and there is a hierarchy of
spaces, every space type has an effect, which is called when the player steps on it.
The second version uses neither map nor spaces types, but only number of boxes. 

The final version of the application uses the second approach, because i think it's a more scala-like solution.
However both the implementation works and passes the tests.

**N.B:** there is duplicated code between the "hierarchy" and the "normal" version of the game, i think it was a waste of time to create a generalization 
to factorize that code. The "hierarchy" version is left in the repo because it is complete and working,
and it is an alternative way to manage the problem. I think it would have been a shame to delete it.

## Libraries

* [ScalaTest](http://www.scalatest.org/) - For unit testing

## Authors

* **Luca Savoja**

## License

This project is licensed under the GNU 3 License - see the [LICENSE.md](LICENSE.md) file for details