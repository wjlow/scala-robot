package robot

import cats.data._
import cats.instances.all._
import cats.syntax.all._
import robot.models.Robot
import ReportAction.monoid
import cats.kernel.Monoid

object Main {

  def main(args: Array[String]): Unit = {
    if (args.length > 0) {
      val commands = io.Source.fromFile(args(0)).getLines() map Parser.parseCommand
      val oneBigCommand = Monoid[RobotRunner].combineAll(commands)
      val initialRobot = Option.empty[Robot]
      oneBigCommand.run(initialRobot)
    } else println("Example usage: sbt \"run src/main/resources/input1.txt\"")
  }
}
