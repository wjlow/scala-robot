package robot

import cats.data.{Writer, State}
import robot.Commands.applyAll
import robot.Parser._
import robot.Robot.{North, Position, ToyRobot}
import org.atnos.eff._
import org.atnos.eff.Eff._
import org.atnos.eff.syntax.all._

object Main {

  case class ProgramState(robot: Option[ToyRobot], commands: Seq[ToyRobot => ToyRobot], outputToPrint: Option[String])

  def main(args: Array[String]): Unit = {
    type Fx = Fx.fx3[Either[AppError, ?], State[Option[ToyRobot], ?], Writer[String, ?]]

    val commands: List[Eff[Fx, Unit]] = List("PLACE 0,0,NORTH", "MOVE", "MOVE", "RIGHT", "REPORT", "MOVE", "REPORT", "RIGHT", "MOVE", "REPORT") map parseCommand[Fx]

    val robot = Option.empty[ToyRobot]
    val p: Eff[Fx, Unit] = commands.reduce(_ >> _)

    val r = p.runEither.runState(robot).map(_._2).runWriterUnsafe[String](println).run

  }

  //  def robotProgram(line: String)(programState: ProgramState): ProgramState = {
  //    programState.robot match {
  //      case Some(robot) =>
  //        if (line == "REPORT") {
  //          val newRobot = applyAll(robot, programState.commands)
  //          ProgramState(Some(newRobot), Nil, Some(newRobot.toString))
  //        } else {
  //          ProgramState(programState.robot, programState.commands :+ parseCommand(line), None)
  //        }
  //      case None => ProgramState(parsePlace(line), Nil, None)
  //    }
  //  }

}
