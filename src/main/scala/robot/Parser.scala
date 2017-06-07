package robot

import cats.Monoid
import robot.models._

import scala.util.Try

object Parser {

  def parseDirection(direction: String): Option[Direction] = {
    direction match {
      case "NORTH" => Option(North)
      case "SOUTH" => Option(South)
      case "EAST" => Option(East)
      case "WEST" => Option(West)
      case _ => None
    }
  }

  def parsePosition(x: String, y: String): Option[Position] =
    Try(Position(x.toInt, y.toInt)).toEither match {
      case Right(pos) if Table.isValidPosition(pos) => Option(pos)
      case Left(err) => None
    }

  def parseCommand(command: String)(implicit m: Monoid[RobotRunner]): RobotRunner =
    command match {
      case "MOVE" => Functions.move
      case "LEFT" => Functions.left
      case "RIGHT" => Functions.right
      case "REPORT" => Functions.report
      case str => parsePositionDirection(str) map (posDir => Functions.place(posDir.toRobot)) getOrElse m.empty
    }

  private def parsePositionDirection(line: String): Option[PositionDirection] = {
    val lineSplit = line.split("\\s+")
    if (lineSplit.nonEmpty) {
      lineSplit(0) match {
        case "PLACE" =>
          val positionDirectionSplit = lineSplit(1).split(",")
          for {
            position <- parsePosition(positionDirectionSplit(0), positionDirectionSplit(1))
            direction <- parseDirection(positionDirectionSplit(2))
          } yield PositionDirection(position, direction)
        case _ => None
      }
    } else None
  }

}
