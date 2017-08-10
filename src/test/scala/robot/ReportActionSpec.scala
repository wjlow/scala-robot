package robot

import cats.implicits._
import org.scalacheck.Prop
import org.scalacheck.Prop._
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import robot.ScalaCheckUtils._
import robot.models._

class ReportActionSpec extends Specification with ScalaCheck {

  "Monoid implementation" should {

    "obey associativity" in {

      Prop.forAll { (x: ReportAction, y: ReportAction, z: ReportAction) =>
        val f = (x |+| y) |+| z
        val g = x |+| (y |+| z)

        f ?= g
      }

    }

    "obey right identity" in {

      Prop.forAll { (x: ReportAction) =>
        x ?= (x |+| ReportAction.empty)
      }

    }

    "obey left identity" in {

      Prop.forAll { (x: ReportAction) =>
        (x |+| ReportAction.empty) ?= x
      }

    }

    "combine with new line character" in {

      Prop.forAll { (x: ReportAction, y: ReportAction) =>
        val combined = x |+| y

        (x, y) match {
          case (ReportAction(""), _) => combined ?= y
          case (_, ReportAction("")) => combined ?= x
          case (x, y) =>
            val tokens = combined.msg.split("\n")
            (tokens(0), tokens(1)) ?= (x.msg, y.msg)
        }

      }

    }


  }

}
