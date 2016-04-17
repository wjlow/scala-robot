package wjlow

object Robot {

  case class Position(x: Int, y: Int)

  sealed trait Direction

  case object North extends Direction

  case object South extends Direction

  case object East extends Direction

  case object West extends Direction

  def isValidPosition(position: Position) = {
    (0 to 4 contains position.x) && (0 to 4 contains position.y)
  }

  def move(position: Position, direction: Direction): (Position, Direction) = {

    val nextPosition = direction match {
      case North => Position(position.x, position.y + 1)
      case South => Position(position.x, position.y - 1)
      case East => Position(position.x + 1, position.y)
      case West => Position(position.x - 1, position.y)
    }

    if (isValidPosition(nextPosition)) (nextPosition, direction) else (position, direction)

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

  def place(currentPositionDirection: (Position, Direction), nextPosition: Position, nextDirection: Direction): (Position, Direction) = {
    if (isValidPosition(nextPosition)) (nextPosition, nextDirection) else currentPositionDirection
  }

}
