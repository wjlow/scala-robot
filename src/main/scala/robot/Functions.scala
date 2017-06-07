package robot

import cats.data.{OptionT, Writer, WriterT}
import cats._
import cats.syntax.show._
import robot.models._
import cats.instances.all._

object Functions {

  def move: RobotRunner =
    RobotRunner {
      _ map { robot =>
        val position = robot.position
        val direction = robot.direction

        val nextPosition = direction match {
          case North => Position(position.x, position.y + 1)
          case South => Position(position.x, position.y - 1)
          case East => Position(position.x + 1, position.y)
          case West => Position(position.x - 1, position.y)
        }

        if (Table.isValidPosition(nextPosition)) Robot(nextPosition, direction)
        else robot
      }
    }

  def right: RobotRunner =
    RobotRunner {
      _ map { robot =>
        val nextDirection = robot.direction match {
          case North => East
          case East => South
          case South => West
          case West => North
        }

        Robot(robot.position, nextDirection)
      }
    }

  def left: RobotRunner =
    right ~> right ~> right

  def place(nextRobot: Robot): RobotRunner =
    RobotRunner { transformer =>
      val optRobot = transformer.run.map(_._2)
      if (Table.isValidPosition(nextRobot.position)) WriterT.lift[Option, ReportAction, Robot](Option(nextRobot))
      else WriterT.lift[Option, ReportAction, Robot](optRobot)
    }


  def report: RobotRunner =
    RobotRunner {
      transformer =>
        val toPrint: String = transformer.value.map(_.show).getOrElse("")
        transformer.tell(ReportAction(toPrint))
    }
}