package wjlow

import wjlow.Robot._

object Commands {

  def isValidPosition(position: Position) = {
    (0 to 4 contains position.x) && (0 to 4 contains position.y)
  }

  def move(position: Position, direction: Direction): ToyRobot = {

    val nextPosition = direction match {
      case North => Position(position.x, position.y + 1)
      case South => Position(position.x, position.y - 1)
      case East => Position(position.x + 1, position.y)
      case West => Position(position.x - 1, position.y)
    }

    if (isValidPosition(nextPosition)) ToyRobot(nextPosition, direction) else ToyRobot(position, direction)

  }

  def left(direction: Direction): Direction = {
    direction match {
      case North => West
      case West => South
      case South => East
      case East => North
    }
  }

  def right(direction: Direction): Direction = {
    direction match {
      case North => East
      case East => South
      case South => West
      case West => North
    }
  }

  def place(currentPositionDirection: ToyRobot, nextPosition: Position, nextDirection: Direction): ToyRobot = {
    if (isValidPosition(nextPosition)) ToyRobot(nextPosition, nextDirection) else currentPositionDirection
  }

}
