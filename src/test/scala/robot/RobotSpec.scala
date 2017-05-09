//package wjlow
//
//import org.scalacheck.{Arbitrary, Gen}
//import org.scalactic.TypeCheckedTripleEquals
//import org.scalatest.prop.PropertyChecks
//import org.scalatest.{FunSpec, Matchers}
//import wjlow.Robot._
//
//class RobotSpec extends FunSpec with TypeCheckedTripleEquals with Matchers with PropertyChecks {
//
//  describe("toString") {
//
//    it("should give a String representation of a Position") {
//      forAll { (x: Int, y: Int) =>
//        Position(x, y).toString should ===(s"$x,$y")
//      }
//    }
//
//    it("should give a String representation of a ToyRobot in caps") {
//
//      implicit val arbitraryDirection: Arbitrary[Direction] = Arbitrary(Gen.oneOf(Seq(North, South, East, West)))
//
//      forAll { (x: Int, y: Int, direction: Direction) =>
//        ToyRobot(Position(x, y), direction).toString should ===(s"$x,$y,${direction.toString.toUpperCase}")
//      }
//
//    }
//
//  }
//
//}
