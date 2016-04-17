package wjlow

object Robot {

  case class Position(x: Int, y: Int)

  sealed trait Direction
  case object North extends Direction
  case object South extends Direction
  case object East extends Direction
  case object West extends Direction

  case class ToyRobot(position: Position, direction: Direction)

}
