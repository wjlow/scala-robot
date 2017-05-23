package robot.models

import cats._

case class Position(x: Int, y: Int) {
  override def toString = s"$x,$y"
}

sealed abstract class Direction(val name: String)

case object North extends Direction("NORTH")

case object South extends Direction("SOUTH")

case object East extends Direction("EAST")

case object West extends Direction("WEST")

case class PositionDirection(position: Position, direction: Direction)

case class Robot(position: Position, direction: Direction)

object Robot {
  implicit val RobotShow: Show[Robot] =
    (robot: Robot) => s"${robot.position.x},${robot.position.y},${robot.direction.name.toUpperCase}"
}

sealed trait AppError

case object ParseError extends AppError

object AppError {
  def parseError: AppError = ParseError
}