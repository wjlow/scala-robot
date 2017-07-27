package robot.models

import cats.{Monoid, Show}

case class ReportAction(msg: String)

object ReportAction {

  implicit val monoid: Monoid[ReportAction] = new Monoid[ReportAction] {
    override def empty: ReportAction =
      ReportAction("")

    override def combine(x: ReportAction, y: ReportAction): ReportAction =
      (x, y) match {
        case (ReportAction(""), y) => y
        case (x, ReportAction("")) => x
        case (x, y) => ReportAction(s"${x.msg}\n${y.msg}")
      }
  }

  implicit val show: Show[ReportAction] = (f: ReportAction) => f.msg

  val empty: ReportAction = monoid.empty

}