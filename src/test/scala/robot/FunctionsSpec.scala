package robot

import cats.data.Writer
import cats.implicits._
import org.scalacheck.Prop
import org.scalacheck.Prop._
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import robot.ScalaCheckUtils._
import robot.models._

class FunctionsSpec extends Specification with ScalaCheck {

  "report" should {

    "return current position/direction for Some(robot)" in {
      forAll { (robot: Robot) =>
        // given
        val input = Writer(ReportAction.empty, Option(robot))

        // when
        val (report, _) = Functions.report.run(input).run

        // then
        report.msg ?= robot.show
      }
    }

    "return current position/direction for None" in {
      // given
      val input = Writer(ReportAction.empty, Option.empty[Robot])

      // when
      val (report, _) = Functions.report.run(input).run

      // then
      report.msg ?= ""
    }

    "return robot unchanged" in {
      forAll { (optRobot: Option[Robot]) =>
        // given
        val input = Writer(ReportAction.empty, optRobot)

        // when
        val (_, result) = Functions.report.run(input).run

        //then
        result ?= optRobot
      }

    }
  }

}