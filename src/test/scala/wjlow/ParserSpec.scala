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

    it("should return ErrorMessage for invalid coordinates") {
      val errorOrRobot = parsePlace("PLACE 5,5,NORTH")
      errorOrRobot.isLeft should ===(true)
      errorOrRobot.swap foreach (errorMessage => errorMessage should ===("Position must be inside 5x5 grid."))
    }

    it("should return ErrorMessage for invalid PLACE command") {
      val errorOrRobot = parsePlace("BAD COMMAND")
      errorOrRobot.isLeft should ===(true)
      errorOrRobot.swap foreach (errorMessage => errorMessage should ===("Invalid PLACE command."))
    }

    it("should return ErrorMessage for invalid Direction") {
      val errorOrRobot = parsePlace("PLACE 1,2,INVALIDDIRECTION")
      errorOrRobot.isLeft should ===(true)
      errorOrRobot.swap foreach (errorMessage => errorMessage should ===("Invalid Direction provided."))
    }

    it("should return ErrorMessage for invalid X-coordinate") {
      val errorOrRobot = parsePlace("PLACE INVALID,Y,SOUTH")
      errorOrRobot.isLeft should ===(true)
      errorOrRobot.swap foreach (errorMessage => errorMessage should ===("Invalid Position provided."))
    }

    it("should return ErrorMessage for invalid Y-coordinate") {
      val errorOrRobot = parsePlace("PLACE X,INVALID,SOUTH")
      errorOrRobot.isLeft should ===(true)
      errorOrRobot.swap foreach (errorMessage => errorMessage should ===("Invalid Position provided."))
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
