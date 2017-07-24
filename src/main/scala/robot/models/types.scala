package robot.models

import cats._
import cats.data.Writer
import cats.syntax.show

case class Position(x: Int, y: Int) {
  override def toString = s"$x,$y"
}

sealed abstract class Direction(val name: String)

case object North extends Direction("NORTH")

case object South extends Direction("SOUTH")

case object East extends Direction("EAST")

case object West extends Direction("WEST")

case class PositionDirection(position: Position, direction: Direction) {
  def toRobot: Robot = Robot(position, direction)
}

case class Robot(position: Position, direction: Direction)

object Robot {
  implicit val show: Show[Robot] =
    (robot: Robot) => s"${robot.position.x},${robot.position.y},${robot.direction.name.toUpperCase}"

  val initial: Writer[ReportAction, Option[Robot]] =
    Writer.value(Option.empty[Robot])(ReportAction.monoid)
}

sealed trait AppError

case object ParseError extends AppError

object AppError {
  def parseError: AppError = ParseError
}