package wjlow

import wjlow.Commands._
import wjlow.Robot._

import scalaz._

object Parser {

  case class PositionDirection(position: Position, direction: Direction)

  def parsePlace(line: String): Option[ToyRobot] = {
    parsePositionDirection(line) map (positionDirection => ToyRobot(positionDirection.position, positionDirection.direction))
  }

  def parseDirection(direction: String): Option[Direction] = {
    direction match {
      case "NORTH" => Some(North)
      case "SOUTH" => Some(South)
      case "EAST" => Some(East)
      case "WEST" => Some(West)
      case _ => None
    }
  }

  def parsePosition(x: String, y: String): Option[Position] = {
    for {
      position <- \/.fromTryCatchNonFatal(Position(x.toInt, y.toInt)).toOption
      if isValidPosition(position)
    } yield position
  }

  def parseCommand(command: String): ToyRobot => ToyRobot = {
    command match {
      case "MOVE" => move
      case "LEFT" => left
      case "RIGHT" => right
      case str =>
        parsePositionDirection(str) map
          (positionDirection => place(positionDirection.position, positionDirection.direction)(_)) getOrElse
          (identity: ToyRobot => ToyRobot)
    }
  }

  private def parsePositionDirection(line: String): Option[PositionDirection] = {
    val lineSplit = line.split("\\s+")
    lineSplit(0) match {
      case "PLACE" =>
        val positionDirectionSplit = lineSplit(1).split(",")
        for {
          position <- parsePosition(positionDirectionSplit(0), positionDirectionSplit(1))
          direction <- parseDirection(positionDirectionSplit(2))
        } yield PositionDirection(position, direction)
      case _ => None
    }
  }

}
