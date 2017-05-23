//package scala.wjlow
//
//import org.scalactic.TypeCheckedTripleEquals
//import org.scalatest.{FunSpec, Matchers}
//import wjlow.Commands._
//import wjlow.Robot._
//
//class CommandsSpec extends FunSpec with TypeCheckedTripleEquals with Matchers {
//
//  describe("move") {
//
//    describe("when facing North") {
//
//      it("should increment Y-coordinate and face North") {
//        val oldRobot = Robot(Position(3, 3), North)
//
//        val newRobot = move(oldRobot)
//        newRobot.position should ===(Position(3, 4))
//        newRobot.direction should ===(North)
//      }
//
//      it("should do nothing if Position is on Northern edge") {
//        val oldRobot = Robot(Position(3, 4), North)
//        val newRobot = move(oldRobot)
//        newRobot should ===(oldRobot)
//      }
//
//    }
//
//    describe("when facing South") {
//
//      it("should decrement Y-coordinate and face South") {
//        val oldRobot = Robot(Position(3, 3), South)
//
//        val newRobot = move(oldRobot)
//        newRobot.position should ===(Position(3, 2))
//        newRobot.direction should ===(South)
//      }
//
//      it("should do nothing if Position is on Southern edge") {
//        val oldRobot = Robot(Position(3, 0), South)
//        val newRobot = move(oldRobot)
//        newRobot should ===(oldRobot)
//      }
//
//    }
//
//    describe("when facing East") {
//
//      it("should increment X-coordinate and face East") {
//        val oldRobot = Robot(Position(3, 3), East)
//
//        val newRobot = move(oldRobot)
//        newRobot.position should ===(Position(4, 3))
//        newRobot.direction should ===(East)
//      }
//
//      it("should do nothing if Position is on Eastern edge") {
//        val oldRobot = Robot(Position(4, 3), East)
//        val newRobot = move(oldRobot)
//        newRobot should ===(oldRobot)
//      }
//    }
//
//    describe("when facing West") {
//
//      it("should decrement X-coordinate and face West") {
//        val oldRobot = Robot(Position(3, 3), West)
//
//        val newRobot = move(oldRobot)
//        newRobot.position should ===(Position(2, 3))
//        newRobot.direction should ===(West)
//      }
//
//      it("should do nothing if Position is on Western edge") {
//        val oldRobot = Robot(Position(0, 3), West)
//        val newRobot = move(oldRobot)
//        newRobot should ===(oldRobot)
//      }
//
//    }
//
//  }
//
//  describe("left") {
//
//    it("should change Direction from North to West") {
//      val oldRobot = Robot(Position(0, 0), North)
//
//      val newRobot = left(oldRobot)
//      newRobot.position should ===(oldRobot.position)
//      newRobot.direction should ===(West)
//    }
//
//    it("should change Direction from West to South") {
//      val oldRobot = Robot(Position(0, 0), West)
//
//      val newRobot = left(oldRobot)
//      newRobot.position should ===(oldRobot.position)
//      newRobot.direction should ===(South)
//    }
//
//    it("should change Direction from South to East") {
//      val oldRobot = Robot(Position(0, 0), South)
//
//      val newRobot = left(oldRobot)
//      newRobot.position should ===(oldRobot.position)
//      newRobot.direction should ===(East)
//    }
//
//    it("should change Direction from East to North") {
//      val oldRobot = Robot(Position(0, 0), East)
//
//      val newRobot = left(oldRobot)
//      newRobot.position should ===(oldRobot.position)
//      newRobot.direction should ===(North)
//    }
//
//  }
//
//  describe("right") {
//
//    it("should change Direction from North to East") {
//      val oldRobot = Robot(Position(0, 0), North)
//
//      val newRobot = right(oldRobot)
//      newRobot.position should ===(oldRobot.position)
//      newRobot.direction should ===(East)
//    }
//
//    it("should change Direction from East to South") {
//      val oldRobot = Robot(Position(0, 0), East)
//
//      val newRobot = right(oldRobot)
//      newRobot.position should ===(oldRobot.position)
//      newRobot.direction should ===(South)
//    }
//
//    it("should change Direction from South to West") {
//      val oldRobot = Robot(Position(0, 0), South)
//
//      val newRobot = right(oldRobot)
//      newRobot.position should ===(oldRobot.position)
//      newRobot.direction should ===(West)
//    }
//
//    it("should change Direction from West to North") {
//      val oldRobot = Robot(Position(0, 0), West)
//
//      val newRobot = right(oldRobot)
//      newRobot.position should ===(oldRobot.position)
//      newRobot.direction should ===(North)
//    }
//
//  }
//
//  describe("place") {
//
//    it("should return next Position and next Direction if next Position is valid") {
//      val currentPositionDirection = Robot(Position(0, 0), North)
//      place(Position(2, 2), West)(currentPositionDirection) should ===(Robot(Position(2, 2), West))
//    }
//
//    it("should return current Position and current Direction if next Position is invalid") {
//      val currentPositionDirection = Robot(Position(0, 0), North)
//      place(Position(-1, -1), West)(currentPositionDirection) should ===(currentPositionDirection)
//    }
//
//  }
//
//  describe("applyAll") {
//
//    it("should apply Commands to Robot in sequence") {
//      val oldRobot = Robot(Position(2, 2), East)
//      val commands = Seq(move(_), left(_), move(_), right(_), move(_), place(Position(4, 4), South)(_), move(_), move(_))
//
//      val newRobot = applyAll(oldRobot, commands)
//      newRobot.position should ===(Position(4, 2))
//      newRobot.direction should ===(South)
//    }
//
//    it("should do nothing to Robot if Commands is Empty") {
//      val oldRobot = Robot(Position(2, 2), East)
//      val commands = Nil
//
//      val newRobot = applyAll(oldRobot, commands)
//      newRobot should ===(oldRobot)
//    }
//
//  }
//
//}