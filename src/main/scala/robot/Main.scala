package robot

import cats.kernel.Monoid
import cats.syntax.all._
import robot.models._

object Main {

  def main(args: Array[String]): Unit = {
    if (args.length > 0) {
      val commands = io.Source.fromFile(args(0)).getLines() map Parser.parseCommand
      val oneBigCommand = Monoid[RobotRunner].combineAll(commands)
      val output = oneBigCommand
        .run(Robot.initial)
        .run
        .map(_._1.show)
      println(output)
    } else println("Example usage: sbt \"run src/main/resources/input1.txt\"")
  }
}
