package robot

import cats.syntax.show._
import org.atnos.eff.WriterCreation._
import org.atnos.eff._
import org.atnos.eff.syntax.all._
import robot.models.Robot._
import robot.models._
import robot.models.effects._

object Commands {

  def move[R: _robotState](): Eff[R, Unit] =
    StateCreation.modify[R, Option[Robot]] { optRobot =>
      optRobot map { robot =>
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

  def turnRight[R: _robotState](): Eff[R, Unit] =
    StateCreation.modify[R, Option[Robot]] { optRobot =>
      optRobot map { robot =>
        val nextDirection = robot.direction match {
          case North => East
          case East => South
          case South => West
          case West => North
        }

        Robot(robot.position, nextDirection)
      }
    }

  def turnLeft[R: _robotState](): Eff[R, Unit] =
    StateCreation.modify[R, Option[Robot]] { optRobot =>
      optRobot map { robot =>
        val nextDirection = robot.direction match {
          case North => West
          case West => South
          case South => East
          case East => North
        }

        Robot(robot.position, nextDirection)
      }
    }

  def place[R: _robotState](nextRobot: Robot): Eff[R, Unit] =
    StateCreation.modify[R, Option[Robot]] { optRobot =>
        if (Table.isValidPosition(nextRobot.position)) Option(nextRobot)
        else optRobot
    }

  def report[R: _logger : _robotState](): Eff[R, Unit] =
    for {
      optRobot <- StateCreation.get[R, Option[Robot]]
      _ <- optRobot match {
        case Some(r) => tell(r.show)
        case None => ().pureEff[R]
      }
    } yield ()

}
