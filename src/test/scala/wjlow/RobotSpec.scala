package scala.wjlow

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{FunSpec, Matchers}
import wjlow.Robot
import wjlow.Robot._

class RobotSpec extends FunSpec with TypeCheckedTripleEquals with Matchers {

  describe("move") {

    describe("when facing North") {

      it("should increment Y-coordinate and face North") {
        val position = Position(3, 3)
        val direction = North

        val robot = Robot.move(position, direction)
        robot.position should ===(Position(3, 4))
        robot.direction should ===(North)
      }

      it("should do nothing if Position is on Northern edge") {
        val position = Position(3, 4)
        val direction = North

        val robot = Robot.move(position, direction)
        robot.position should ===(position)
        robot.direction should ===(direction)
      }

    }

    describe("when facing South") {

      it("should decrement Y-coordinate and face South") {
        val position = Position(3, 3)
        val direction = South

        val robot = Robot.move(position, direction)
        robot.position should ===(Position(3, 2))
        robot.direction should ===(South)
      }

      it("should do nothing if Position is on Southern edge") {
        val position = Position(3, 0)
        val direction = South

        val robot = Robot.move(position, direction)
        robot.position should ===(position)
        robot.direction should ===(direction)
      }

    }

    describe("when facing East") {

      it("should increment X-coordinate and face East") {
        val position = Position(3, 3)
        val direction = East

        val robot = Robot.move(position, direction)
        robot.position should ===(Position(4, 3))
        robot.direction should ===(East)
      }

      it("should do nothing if Position is on Eastern edge") {
        val position = Position(4, 3)
        val direction = East

        val robot = Robot.move(position, direction)
        robot.position should ===(position)
        robot.direction should ===(direction)
      }
    }

    describe("when facing West") {

      it("should decrement X-coordinate and face West") {
        val position = Position(3, 3)
        val direction = West

        val robot = Robot.move(position, direction)
        robot.position should ===(Position(2, 3))
        robot.direction should ===(West)
      }

      it("should do nothing if Position is on Western edge") {
        val position = Position(0, 3)
        val direction = West

        val robot = Robot.move(position, direction)
        robot.position should ===(position)
        robot.direction should ===(direction)
      }

    }

  }

  describe("left") {

    it("should change Direction from North to West") {
      left(North) should ===(West)
    }

    it("should change Direction from West to South") {
      left(West) should ===(South)
    }

    it("should change Direction from South to East") {
      left(South) should ===(East)
    }

    it("should change Direction from East to North") {
      left(East) should ===(North)
    }

  }

  describe("right") {

    it("should change Direction from North to East") {
      right(North) should ===(East)
    }

    it("should change Direction from East to South") {
      right(East) should ===(South)
    }

    it("should change Direction from South to West") {
      right(South) should ===(West)
    }

    it("should change Direction from West to North") {
      right(West) should ===(North)
    }

  }

  describe("place") {

    it("should return next Position and next Direction if next Position is valid") {
      val currentPositionDirection = ToyRobot(Position(0, 0), North)
      place(currentPositionDirection, Position(2, 2), West) should ===(ToyRobot(Position(2, 2), West))
    }

    it("should return current Position and current Direction if next Position is invalid") {
      val currentPositionDirection = ToyRobot(Position(0, 0), North)
      place(currentPositionDirection, Position(-1, -1), West) should ===(currentPositionDirection)
    }

  }

}