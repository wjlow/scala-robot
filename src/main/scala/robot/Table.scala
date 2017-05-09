package robot

import robot.Robot.Position

object Table {

  val widthRange = 0 to 4
  val heightRange = 0 to 4

  def isValidPosition(position: Position): Boolean = {
    (widthRange contains position.x) && (heightRange contains position.y)
  }

}
