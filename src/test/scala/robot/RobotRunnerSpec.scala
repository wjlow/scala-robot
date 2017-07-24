package robot

import cats.data.{Writer, WriterT}
import org.scalacheck.{Gen, Prop}
import org.scalacheck.Prop._
import org.specs2.mutable.Specification
import robot.models._
import org.specs2.ScalaCheck
import cats._
import cats.implicits._

class RobotRunnerSpec extends Specification with ScalaCheck {

  val genRobot: Gen[Robot] =
    for {
      x <- Gen.choose(0, 4)
      y <- Gen.choose(0, 4)
      pos = Position(x, y)
      dir <- Gen.oneOf(North, South, East, West)
    } yield Robot(pos, dir)

  val genReportAction: Gen[ReportAction] =
    for {
      str <- Gen.alphaStr
    } yield ReportAction(str)

  val genWriterOptRobot: Gen[Writer[ReportAction, Option[Robot]]] =
    for {
      reportAction <- genReportAction
      optRobot <- Gen.option(genRobot)
    } yield Writer(reportAction, optRobot)

  val genRobotRunner: Gen[RobotRunner] =
    for {
      next <- genRobot
      f <- Gen.oneOf(Functions.left, Functions.right, Functions.move, Functions.report, Functions.place(next))
    } yield f

  "RobotRunner monoid laws" should {

    "associativity" in {

      Prop.forAll(genRobotRunner, genRobotRunner, genRobotRunner, genWriterOptRobot) { (x: RobotRunner, y: RobotRunner, z: RobotRunner, r: Writer[ReportAction, Option[Robot]]) =>
        val f = (x |+| y) |+| z
        val g = x |+| (y |+| z)

        val r1 = f.run(r)
        val r2 = g.run(r)
        println(s"r1: $r1")
        println(s"r2: $r2")
        r1 ?= r2
      }

    }

    "right identity" in {

      Prop.forAll(genRobotRunner, genWriterOptRobot) { (x: RobotRunner, r: Writer[ReportAction, Option[Robot]]) =>
        x.run(r) ?= (x |+| RobotRunner.empty).run(r)
      }

    }

    "left identity" in {

      Prop.forAll(genRobotRunner, genWriterOptRobot) { (x: RobotRunner, r: Writer[ReportAction, Option[Robot]]) =>
        (x |+| RobotRunner.empty).run(r) ?= x.run(r)
      }

    }

  }
}
