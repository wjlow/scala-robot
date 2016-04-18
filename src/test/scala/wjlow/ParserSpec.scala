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
      val maybeRobot = parsePlace("PLACE 1,2,NORTH")
      maybeRobot.isDefined should ===(true)
      maybeRobot foreach (robot => {
        robot.position should ===(Position(1, 2))
        robot.direction should ===(North)
      })
    }

    it("should return None for invalid Position") {
      val maybeRobot = parsePlace("PLACE 5,5,NORTH")
      maybeRobot.isEmpty should ===(true)
    }

    it("should return None for invalid PLACE command") {
      val maybeRobot = parsePlace("BAD COMMAND")
      maybeRobot.isEmpty should ===(true)
    }

    it("should return None for invalid Direction") {
      val maybeRobot = parsePlace("PLACE 1,2,INVALIDDIRECTION")
      maybeRobot.isEmpty should ===(true)
    }

    it("should return None for invalid X-coordinate") {
      val maybeRobot = parsePlace("PLACE INVALID,Y,SOUTH")
      maybeRobot.isEmpty should ===(true)
    }

    it("should return None for invalid Y-coordinate") {
      val maybeRobot = parsePlace("PLACE X,INVALID,SOUTH")
      maybeRobot.isEmpty should ===(true)
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

  describe("parsePosition") {

    it("should return Some Position") {
      val zeroToFour = for (n <- Gen.choose(0, 4)) yield n

      forAll(zeroToFour, zeroToFour) { (x: Int, y: Int) =>
        val maybePosition = parsePosition(x.toString, y.toString)
        maybePosition should ===(Some(Position(x, y)))
      }
    }

    it("should return None if not a valid Position") {
      val maybePosition = parsePosition("5", "5")
      maybePosition should ===(None)
    }

    it("should return None if X cannot be converted to Int") {
      val maybePosition = parsePosition("INVALID-X", "1")
      maybePosition should ===(None)
    }

    it("should return None if Y cannot be converted to Int") {
      val maybePosition = parsePosition("1", "INVALID-Y")
      maybePosition should ===(None)
    }

  }

  describe("parseCommand") {

    it("should convert valid input to Move Command that can be applied to a ToyRobot") {
      val oldRobot = ToyRobot(Position(0, 0), North)
      val newRobot = parseCommand("MOVE")(oldRobot)
      newRobot.position should ===(Position(0, 1))
      newRobot.direction should ===(North)
    }

    it("should convert valid input to Left Command that can be applied to a ToyRobot") {
      val oldRobot = ToyRobot(Position(0, 0), North)
      val newRobot = parseCommand("LEFT")(oldRobot)
      newRobot.position should ===(Position(0, 0))
      newRobot.direction should ===(West)
    }

    it("should convert valid input to Right Command that can be applied to a ToyRobot") {
      val oldRobot = ToyRobot(Position(0, 0), North)
      val newRobot = parseCommand("RIGHT")(oldRobot)
      newRobot.position should ===(Position(0, 0))
      newRobot.direction should ===(East)
    }

    it("should convert valid input to Place Command that can be applied to a ToyRobot") {
      val oldRobot = ToyRobot(Position(0, 0), North)
      val newRobot = parseCommand("PLACE 1,2,SOUTH")(oldRobot)
      newRobot.position should ===(Position(1, 2))
      newRobot.direction should ===(South)
    }

    it("should convert Report Command to Identity Command that does nothing to a ToyRobot") {
      val oldRobot = ToyRobot(Position(0, 0), North)
      val newRobot = parseCommand("REPORT")(oldRobot)
      newRobot should ===(oldRobot)
    }

    it("should convert Place Command with invalid Position to Identity Command that does nothing to a ToyRobot") {
      val oldRobot = ToyRobot(Position(0, 0), North)
      val newRobot = parseCommand("PLACE X,Y,SOUTH")(oldRobot)
      newRobot should ===(oldRobot)
    }

    it("should convert Place Command with invalid Direction to Identity Command that does nothing to a ToyRobot") {
      val oldRobot = ToyRobot(Position(0, 0), North)
      val newRobot = parseCommand("PLACE 1,2,INVALID")(oldRobot)
      newRobot should ===(oldRobot)
    }

    it("should convert unknown Command to Identity Command that does nothing to a ToyRobot") {
      val oldRobot = ToyRobot(Position(0, 0), North)
      val newRobot = parseCommand("UNKNOWN COMMAND")(oldRobot)
      newRobot should ===(oldRobot)
    }

  }

}
