package robot

import cats.data.WriterT
import cats.instances.all._
import cats.syntax.all._
import robot.models._

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
    right |+| right |+| right

  def place(nextRobot: Robot): RobotRunner =
    RobotRunner { optRobot =>
      if (Table.isValidPosition(nextRobot.position)) Option(nextRobot)
      else optRobot
    }


  def report: RobotRunner =
    RobotRunner {
      optRobot =>
        optRobot map (_.show) foreach println
        optRobot
    }
}