package wjlow

import wjlow.Commands.applyAll
import wjlow.Parser._
import wjlow.Robot.ToyRobot

object Main {

  case class ProgramState(robot: Option[ToyRobot], commands: Seq[ToyRobot => ToyRobot], outputToPrint: Option[String])

  def main(args: Array[String]): Unit = {
    var currentRobot = None: Option[ToyRobot]
    var currentCommands = Nil: Seq[ToyRobot => ToyRobot]

    for (line <- io.Source.fromFile(args(0)).getLines()) {
      val programState = robotProgram(line)(ProgramState(currentRobot, currentCommands, None))
      currentRobot = programState.robot
      currentCommands = programState.commands
      programState.outputToPrint foreach println
    }
  }

  def robotProgram(line: String)(programState: ProgramState): ProgramState = {
    programState.robot match {
      case Some(robot) =>
        if (line == "REPORT") {
          val newRobot = applyAll(robot, programState.commands)
          ProgramState(Some(newRobot), Nil, Some(newRobot.toString))
        } else {
          ProgramState(programState.robot, programState.commands :+ parseCommand(line), None)
        }
      case None => ProgramState(parsePlace(line), Nil, None)
    }
  }

}
