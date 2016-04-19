package wjlow

import org.scalacheck.Gen
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSpec, Matchers}
import wjlow.Robot._
import wjlow.Table._

class TableSpec extends FunSpec with TypeCheckedTripleEquals with Matchers with PropertyChecks {

  val validPositionGen = Gen.choose(0, 4)
  val invalidPositionGen = Gen.oneOf(Gen.choose(5, 100), Gen.negNum[Int])

  describe("isValidPosition") {

    it("should return True for valid Position") {
      forAll(validPositionGen, validPositionGen) { (x: Int, y: Int) =>
        isValidPosition(Position(x, y)) should ===(true)
      }
    }

    it("should return False for invalid X") {
      forAll(invalidPositionGen, validPositionGen) { (invalidX: Int, y: Int) =>
        isValidPosition(Position(invalidX, y)) should ===(false)
      }
    }

    it("should return False for invalid Y") {
      forAll(validPositionGen, invalidPositionGen) { (x: Int, invalidY: Int) =>
        isValidPosition(Position(x, invalidY)) should ===(false)
      }
    }

    it("should return False for invalid X and Y") {
      forAll(invalidPositionGen, invalidPositionGen) { (invalidX: Int, invalidY: Int) =>
        isValidPosition(Position(invalidX, invalidY)) should ===(false)
      }
    }

  }

}
