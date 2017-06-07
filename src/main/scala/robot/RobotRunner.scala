package robot

import cats.{Monoid, Semigroup}
import cats.data._
import robot.models.Robot

case class ReportAction(msg: String)

object ReportAction {

  implicit def monoid: Monoid[ReportAction] = new Monoid[ReportAction] {
    override def empty: ReportAction =
      ReportAction("")

    override def combine(x: ReportAction, y: ReportAction): ReportAction =
      (x, y) match {
        case (x, y) if x.msg.isEmpty => y
        case (x, y) if y.msg.isEmpty => x
        case (x, y) => ReportAction(s"${x.msg}\n${y.msg}")
      }
  }

}

case class RobotRunner(run: WriterT[Option, ReportAction, Robot] => WriterT[Option, ReportAction, Robot]) {
  def ~>(other: RobotRunner): RobotRunner =
    RobotRunner(run andThen other.run)
}

object RobotRunner {
  def id: RobotRunner = new RobotRunner(identity)
}
