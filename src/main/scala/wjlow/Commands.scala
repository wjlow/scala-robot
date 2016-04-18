package wjlow

import wjlow.Robot._

object Commands {

  def move(robot: ToyRobot): ToyRobot = {
    val position = robot.position
    val direction = robot.direction

    val nextPosition = direction match {
      case North => Position(position.x, position.y + 1)
      case South => Position(position.x, position.y - 1)
      case East => Position(position.x + 1, position.y)
      case West => Position(position.x - 1, position.y)
    }

    place(nextPosition, direction)(robot)
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
    if (Table.isValidPosition(nextPosition)) ToyRobot(nextPosition, nextDirection) else currentPositionDirection
  }

  def applyAll(robot: ToyRobot, commands: Seq[ToyRobot => ToyRobot]): ToyRobot = {
    val composedCommands = (commands foldLeft(identity: ToyRobot => ToyRobot))((f, g) => f andThen g)
    composedCommands(robot)
  }

}
