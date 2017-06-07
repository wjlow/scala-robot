package robot

import cats.data._
import cats.instances.all._
import robot.models.Robot
import ReportAction.monoid

object Main {

  def main(args: Array[String]): Unit = {
    if (args.length > 0) {
      val commands: Iterator[RobotRunner] = io.Source.fromFile(args(0)).getLines() map Parser.parseCommand
      val oneBigCommand: RobotRunner = commands.foldLeft(RobotRunner.id)(_ ~> _)
      val initialRobot = WriterT.lift[Option, ReportAction, Robot](Option.empty[Robot])
      oneBigCommand.run(initialRobot).run.map(_._1.msg).foreach(println)
    } else println("Example usage: sbt \"run src/main/resources/input1.txt\"")
  }
}
