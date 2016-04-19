package wjlow

import wjlow.Commands.applyAll
import wjlow.Parser._
import wjlow.Robot.ToyRobot

object Main {

  case class ProgramState(robot: Option[ToyRobot], commands: Seq[ToyRobot => ToyRobot], outputToPrint: Option[String])

  def main(args: Array[String]): Unit = {
    if (args.length > 0) {

      val robotPrograms = io.Source.fromFile(args(0)).getLines() map (line => robotProgram(line)(_))
      val programStates = (robotPrograms scanLeft ProgramState(None, Nil, None))((state, f) => f(state))
      val outputToPrint = programStates filter (_.outputToPrint.isDefined) map (_.outputToPrint.get)
      outputToPrint foreach println

    } else println("Example usage: sbt \"run src/main/resources/input1.txt\"")
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
