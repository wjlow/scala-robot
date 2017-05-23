package robot

import cats.data.{State, Writer}
import org.atnos.eff._
import org.atnos.eff.syntax.all._
import robot.Parser._
import robot.models.{AppError, Robot}

object Main {

  def main(args: Array[String]): Unit = {
    type Fx = Fx.fx3[Either[AppError, ?], State[Option[Robot], ?], Writer[String, ?]]

    if (args.length > 0) {
      val commands = io.Source.fromFile(args(0)).getLines() map parseCommand[Fx]
      val robot = Option.empty[Robot]
      val program: Eff[Fx, Unit] = commands.reduce(_ >> _)
      program.runEither.runState(robot).map(_._2).runWriterUnsafe[String](println).run
    } else println("Example usage: sbt \"run src/main/resources/input1.txt\"")

  }
}
