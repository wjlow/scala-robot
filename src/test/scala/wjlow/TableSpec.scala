package wjlow

import org.scalacheck.Gen
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSpec, Matchers}
import wjlow.Robot._
import wjlow.Table._

class TableSpec extends FunSpec with TypeCheckedTripleEquals with Matchers with PropertyChecks {

  describe("isValidPosition") {

    it("should return True for valid Position") {
      val zeroToFour = for (n <- Gen.choose(0, 4)) yield n

      forAll(zeroToFour, zeroToFour) { (x: Int, y: Int) =>
        isValidPosition(Position(x, y)) should ===(true)
      }
    }

    it("should return False for invalid X") {
      val zeroToFour = for (n <- Gen.choose(0, 4)) yield n

      forAll(zeroToFour) { (y: Int) =>
        isValidPosition(Position(5, y)) should ===(false)
      }
    }

    it("should return False for invalid Y") {
      val zeroToFour = for (n <- Gen.choose(0, 4)) yield n

      forAll(zeroToFour) { (x: Int) =>
        isValidPosition(Position(x, 5)) should ===(false)
      }
    }

    it("should return False for invalid X and Y") {
      isValidPosition(Position(5, 5)) should ===(false)
    }

  }

}
