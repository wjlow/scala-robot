package wjlow

import wjlow.Robot._

object Commands {

  def isValidPosition(position: Position) = {
    (0 to 4 contains position.x) && (0 to 4 contains position.y)
  }

  def move(robot: ToyRobot): ToyRobot = {
    val position = robot.position
    val direction = robot.direction

    val nextPosition = direction match {
      case North => Position(position.x, position.y + 1)
      case South => Position(position.x, position.y - 1)
      case East => Position(position.x + 1, position.y)
      case West => Position(position.x - 1, position.y)
    }

    if (isValidPosition(nextPosition)) ToyRobot(nextPosition, direction) else ToyRobot(position, direction)

  }

  def left(robot: ToyRobot): ToyRobot = {
    val nextDirection = robot.direction match {
      case North => West
      case West => South
      case South => East
      case East => North
    }
    ToyRobot(robot.position, nextDirection)
  }

  def right(robot: ToyRobot): ToyRobot = {
    val nextDirection = robot.direction match {
      case North => East
      case East => South
      case South => West
      case West => North
    }
    ToyRobot(robot.position, nextDirection)
  }

  def place(nextPosition: Position, nextDirection: Direction)(currentPositionDirection: ToyRobot): ToyRobot = {
    if (isValidPosition(nextPosition)) ToyRobot(nextPosition, nextDirection) else currentPositionDirection
  }

  def applyAll(robot: ToyRobot, commands: Seq[ToyRobot => ToyRobot]): ToyRobot = {
    val composedCommands = commands.reduce((f, g) => f andThen g)
    composedCommands(robot)
  }

}
