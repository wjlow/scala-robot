package robot

import cats.data.Writer
import cats.implicits._
import org.scalacheck.Prop
import org.scalacheck.Prop._
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import robot.ScalaCheckUtils._
import robot.models._

class RobotRunnerSpec extends Specification with ScalaCheck {

  "Monoid implementation" should {

    "obey associativity" in {

      Prop.forAll { (x: RobotRunner, y: RobotRunner, z: RobotRunner, r: Writer[ReportAction, Option[Robot]]) =>
        val f = (x |+| y) |+| z
        val g = x |+| (y |+| z)

        f.run(r) ?= g.run(r)
      }

    }

    "obey right identity" in {

      Prop.forAll { (x: RobotRunner, r: Writer[ReportAction, Option[Robot]]) =>
        x.run(r) ?= (x |+| RobotRunner.empty).run(r)
      }

    }

    "obey left identity" in {

      Prop.forAll { (x: RobotRunner, r: Writer[ReportAction, Option[Robot]]) =>
        (x |+| RobotRunner.empty).run(r) ?= x.run(r)
      }

    }

  }
}
