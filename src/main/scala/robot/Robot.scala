package robot

import cats._, cats.instances.all._, cats.syntax.show._

object Robot {

  case class Position(x: Int, y: Int) {
    override def toString = s"$x,$y"
  }

  sealed abstract class Direction(val name: String)
  case object North extends Direction("NORTH")
  case object South extends Direction("SOUTH")
  case object East extends Direction("EAST")
  case object West extends Direction("WEST")

  case class ToyRobot(position: Position, direction: Direction)

  implicit val toyRobotShow: Show[ToyRobot] = new Show[ToyRobot] {
    override def show(robot: ToyRobot): String = s"${robot.position.x},${robot.position.y},${robot.direction.name.toUpperCase}"
  }

}
