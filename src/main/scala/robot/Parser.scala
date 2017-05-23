package robot

import org.atnos.eff._
import robot.Commands._
import robot.models._
import robot.models.effects._

import scala.util.Try

object Parser {

  def parsePlace[R: _err](line: String): Eff[R, Robot] = {
    parsePositionDirection(line) map (positionDirection => Robot(positionDirection.position, positionDirection.direction))
  }

  def parseDirection[R: _err](direction: String): Eff[R, Direction] = {
    direction match {
      case "NORTH" => EitherCreation.right(North)
      case "SOUTH" => EitherCreation.right(South)
      case "EAST" => EitherCreation.right(East)
      case "WEST" => EitherCreation.right(West)
      case _ => EitherCreation.left[R, AppError, Direction](AppError.parseError)
    }
  }

  def parsePosition[R: _err](x: String, y: String): Eff[R, Position] =
    Try(Position(x.toInt, y.toInt)).toEither match {
      case Right(pos) if Table.isValidPosition(pos) => EitherCreation.right(pos)
      case Left(err) => EitherCreation.left(AppError.parseError)
    }

  def parseCommand[R: _robotState : _err : _logger](command: String): Eff[R, Unit] =
    command match {
      case "MOVE" => move[R]()
      case "LEFT" => turnLeft[R]()
      case "RIGHT" => turnRight[R]()
      case "REPORT" => report[R]()
      case str => for {
        posDir <- parsePositionDirection[R](str)
        _ <- place[R](Robot(posDir.position, posDir.direction))
      } yield ()
    }

  private def parsePositionDirection[R: _err](line: String): Eff[R, PositionDirection] = {
    val lineSplit = line.split("\\s+")
    if (lineSplit.nonEmpty) {
      lineSplit(0) match {
        case "PLACE" =>
          val positionDirectionSplit = lineSplit(1).split(",")
          for {
            position <- parsePosition(positionDirectionSplit(0), positionDirectionSplit(1))
            direction <- parseDirection(positionDirectionSplit(2))
          } yield PositionDirection(position, direction)
        case _ => EitherCreation.left(AppError.parseError)
      }
    } else EitherCreation.left(AppError.parseError)
  }

}
