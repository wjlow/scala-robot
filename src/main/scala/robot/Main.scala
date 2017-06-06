package robot

import cats.data.{State, Writer}
import org.atnos.eff._
import org.atnos.eff.syntax.all._
import robot.models.{AppError, Robot}

object Main {

  def main(args: Array[String]): Unit = {
    val commands: Iterator[RobotRunner] = io.Source.fromFile(args(0)).getLines() map Parser.parseCommand
    val oneBigCommand: RobotRunner = commands.foldLeft(RobotRunner.id)(_ ~> _)
    val initialRobot = Option.empty[Robot]
    oneBigCommand.run(initialRobot)
  }
}
