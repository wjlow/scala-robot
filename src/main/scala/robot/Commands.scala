package robot

import robot.Robot._
import org.atnos.eff._
import org.atnos.eff.WriterCreation._
import org.atnos.eff.syntax.all._
import cats._
import cats.instances.all._
import cats.syntax.show._

object Commands {

  def move[R: _robotState](): Eff[R, Unit] =
    StateCreation.modify[R, Option[ToyRobot]] { optRobot =>
      optRobot map { robot =>
        val position = robot.position
        val direction = robot.direction

        val nextPosition = direction match {
          case North => Position(position.x, position.y + 1)
          case South => Position(position.x, position.y - 1)
          case East => Position(position.x + 1, position.y)
          case West => Position(position.x - 1, position.y)
        }

        if (Table.isValidPosition(nextPosition)) ToyRobot(nextPosition, direction)
        else robot
      }
    }

  def turnRight[R: _robotState](): Eff[R, Unit] =
    StateCreation.modify[R, Option[ToyRobot]] { optRobot =>
      optRobot map { robot =>
        val nextDirection = robot.direction match {
          case North => East
          case East => South
          case South => West
          case West => North
        }

        ToyRobot(robot.position, nextDirection)
      }
    }

  def turnLeft[R: _robotState](): Eff[R, Unit] =
    StateCreation.modify[R, Option[ToyRobot]] { optRobot =>
      optRobot map { robot =>
        val nextDirection = robot.direction match {
          case North => West
          case West => South
          case South => East
          case East => North
        }

        ToyRobot(robot.position, nextDirection)
      }
    }

  def place[R: _robotState](nextRobot: ToyRobot): Eff[R, Unit] =
    StateCreation.modify[R, Option[ToyRobot]] { optRobot =>
        if (Table.isValidPosition(nextRobot.position)) Option(nextRobot)
        else optRobot
    }

  def report[R: _logger : _robotState](): Eff[R, Unit] =
    for {
      optRobot <- StateCreation.get[R, Option[ToyRobot]]
      _ <- optRobot match {
        case Some(r) => tell(r.show)
        case None => tell("")
      }
    } yield ()

  def applyAll(robot: ToyRobot, commands: Seq[ToyRobot => ToyRobot]): ToyRobot = {
    val composedCommands = (commands foldLeft (identity: ToyRobot => ToyRobot)) ((f, g) => f andThen g)
    composedCommands(robot)
  }

}
