package wjlow

import wjlow.Commands._
import wjlow.Robot._

import scalaz._

object Parser {

  type ErrorMessage = String

  case class ParsedPositionDirection(errorMessageOrPosition: \/[ErrorMessage, Position], maybeDirection: Option[Direction])

  def parsePlace(line: String): \/[ErrorMessage, ToyRobot] = {
    val parsed = parsePositionDirection(line)
    parsed.errorMessageOrPosition match {
      case \/-(position) =>
        parsed.maybeDirection map (direction =>
          parsed.errorMessageOrPosition map (position => ToyRobot(position, direction)) leftMap (_ => "Invalid Position provided.")
          ) getOrElse -\/("Invalid Direction provided.")
      case -\/(errorMessage) => -\/(errorMessage)
    }
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

  def parseCommand(command: String): ToyRobot => ToyRobot = {
    command match {
      case "MOVE" => move
      case "LEFT" => left
      case "RIGHT" => right
      case str =>
        val parsed = parsePositionDirection(str)
        val parsedCommand = {
          for {
            position <- parsed.errorMessageOrPosition.toOption
            direction <- parsed.maybeDirection
          } yield place(position, direction)(_)
        } getOrElse identity: ToyRobot => ToyRobot
        parsedCommand
    }
  }

  private def parsePositionDirection(line: String): ParsedPositionDirection = {
    val lineSplit = line.split("\\s+")
    lineSplit(0) match {
      case "PLACE" =>
        val positionDirectionSplit = lineSplit(1).split(",")
        val throwableOrPosition = \/.fromTryCatchNonFatal(Position(positionDirectionSplit(0).toInt, positionDirectionSplit(1).toInt))
        val errorMessageOrPosition = throwableOrPosition leftMap (_ => "Invalid Position provided.")
        val errorMessageOrValidPosition = errorMessageOrPosition flatMap (position =>
          if (isValidPosition(position))
            \/-(position)
           else
            -\/("Position must be inside 5x5 grid.")
          )

        val maybeDirection = parseDirection(positionDirectionSplit(2))
        ParsedPositionDirection(errorMessageOrValidPosition, maybeDirection)
      case _ => ParsedPositionDirection(-\/("Invalid PLACE command."), None)
    }
  }

}
