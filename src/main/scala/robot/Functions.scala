package robot

import cats._
import cats.data.Writer
import cats.implicits._
import robot.models._

object Functions {

  val WriterOptionFunctor = Functor[Writer[ReportAction, ?]].compose[Option]

  def move: RobotRunner =
    RobotRunner { writerOptRobot =>
      WriterOptionFunctor.map(writerOptRobot) { robot =>
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
    RobotRunner { writerOptRobot =>
      WriterOptionFunctor.map(writerOptRobot) { robot =>
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
    right |+| right |+| right

  def place(nextRobot: Robot): RobotRunner =
    RobotRunner { writerOptRobot =>
      writerOptRobot.map { optRobot =>
        if (Table.isValidPosition(nextRobot.position)) Option(nextRobot)
        else optRobot
      }
    }

  def report: RobotRunner =
    RobotRunner {
      writerOptRobot =>
        val report = ReportAction(writerOptRobot.value.map(_.show).orEmpty)
        writerOptRobot.tell(report)
    }
}