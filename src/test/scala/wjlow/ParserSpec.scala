package wjlow

import org.scalacheck.{Arbitrary, Gen}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSpec, Matchers}
import wjlow.Parser._
import wjlow.Robot._

class ParserSpec extends FunSpec with TypeCheckedTripleEquals with Matchers with PropertyChecks {

  describe("parsePlace") {

    it("should return ToyRobot for valid PLACE command") {
      val errorOrRobot = parsePlace("PLACE 1,2,NORTH")
      errorOrRobot.isRight should ===(true)
      errorOrRobot foreach (robot => {
        robot.position should ===(Position(1, 2))
        robot.direction should ===(North)
      })
    }

    it("should return ErrorMessage for invalid PLACE command") {
      val errorOrRobot = parsePlace("BAD COMMAND")
      errorOrRobot.isLeft should ===(true)
      errorOrRobot.swap foreach(errorMessage => errorMessage should ===("Invalid PLACE command."))
    }

    it("should return ErrorMessage for invalid Direction") {
      val errorOrRobot = parsePlace("PLACE 1,2,INVALIDDIRECTION")
      errorOrRobot.isLeft should ===(true)
      errorOrRobot.swap foreach(errorMessage => errorMessage should ===("Invalid Direction provided."))
    }

  }

  describe("parseDirection") {

    it("should return Some Direction") {

      implicit val genDirection: Arbitrary[Direction] = Arbitrary(Gen.oneOf(Seq(North, South, East, West)))

      forAll { (direction: Direction) =>
        val directionAsString = direction.toString.toUpperCase
        parseDirection(directionAsString) should ===(Some(direction))
      }

    }

    it("should return None if not a valid Direction") {
      parseDirection("INVALID") should ===(None)
    }

  }

}
