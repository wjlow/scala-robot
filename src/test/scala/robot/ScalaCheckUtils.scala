package robot

import cats.data.Writer
import org.scalacheck.{Arbitrary, Gen}
import robot.models._

object ScalaCheckUtils {
  val genRobot: Gen[Robot] =
    for {
      x <- Gen.choose(0, 4)
      y <- Gen.choose(0, 4)
      pos = Position(x, y)
      dir <- Gen.oneOf(North, South, East, West)
    } yield Robot(pos, dir)

  val genReportAction: Gen[ReportAction] =
    Gen.alphaStr.map(ReportAction.apply)

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

  implicit val arbRobot: Arbitrary[Robot] = Arbitrary(genRobot)

  implicit val arbRobotRunner: Arbitrary[RobotRunner] = Arbitrary(genRobotRunner)

  implicit val arbWriterOptRobot: Arbitrary[Writer[ReportAction, Option[Robot]]] = Arbitrary(genWriterOptRobot)

  implicit val arbReportAction: Arbitrary[ReportAction] = Arbitrary(genReportAction)
}