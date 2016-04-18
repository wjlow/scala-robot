package wjlow

object Robot {

  case class Position(x: Int, y: Int) {
    override def toString = s"$x,$y"
  }

  sealed trait Direction
  case object North extends Direction
  case object South extends Direction
  case object East extends Direction
  case object West extends Direction

  case class ToyRobot(position: Position, direction: Direction) {
    override def toString = s"${position.x},${position.y},${direction.toString.toUpperCase}"
  }

}
