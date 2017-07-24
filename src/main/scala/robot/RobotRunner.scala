package robot

import cats.data._
import cats.{Functor, Monoid, Show}
import robot.models.Robot

case class ReportAction(msg: String)

object ReportAction {

  implicit val monoid: Monoid[ReportAction] = new Monoid[ReportAction] {
    override def empty: ReportAction =
      ReportAction("")

    override def combine(x: ReportAction, y: ReportAction): ReportAction =
      (x, y) match {
        case (x, y) if x.msg.isEmpty => y
        case (x, y) if y.msg.isEmpty => x
        case (x, y) => ReportAction(s"${x.msg}\n${y.msg}")
      }
  }

  implicit val show: Show[ReportAction] = (f: ReportAction) => f.msg

}

case class RobotRunner(run: Writer[ReportAction, Option[Robot]] => Writer[ReportAction, Option[Robot]])

object RobotRunner {

  implicit val monoid: Monoid[RobotRunner] = new Monoid[RobotRunner] {
    override def empty: RobotRunner = RobotRunner(identity)

    override def combine(x: RobotRunner, y: RobotRunner): RobotRunner =
      RobotRunner(x.run andThen y.run)
  }

}
