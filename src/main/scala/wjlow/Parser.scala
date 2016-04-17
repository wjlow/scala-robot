package wjlow

import wjlow.Robot._

import scalaz._

object Parser {

  type ErrorMessage = String

  def parsePlace(line: String): \/[ErrorMessage, ToyRobot] = {

    val lineSplit = line.split("\\s+")
    lineSplit(0) match {
      case "PLACE" =>
        val positionDirectionSplit = lineSplit(1).split(",")
        val throwableOrPosition = \/.fromTryCatchNonFatal(Position(positionDirectionSplit(0).toInt, positionDirectionSplit(1).toInt))
        val maybeDirection = parseDirection(positionDirectionSplit(2))

        maybeDirection map (direction =>
          throwableOrPosition map (position => ToyRobot(position, direction)) leftMap (_ => "Invalid Position provided.")
          ) getOrElse -\/("Invalid Direction provided.")

      case _ => -\/("Invalid PLACE command.")
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
}
