package robot.models

import cats.Monoid
import cats.data._

case class RobotRunner(run: Writer[ReportAction, Option[Robot]] => Writer[ReportAction, Option[Robot]])

object RobotRunner {

  implicit val monoid: Monoid[RobotRunner] = new Monoid[RobotRunner] {
    override def empty: RobotRunner = RobotRunner(identity)

    override def combine(x: RobotRunner, y: RobotRunner): RobotRunner =
      RobotRunner(x.run andThen y.run)
  }

  val empty: RobotRunner = Monoid[RobotRunner].empty

}
