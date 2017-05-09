//package wjlow
//
//import org.scalactic.TypeCheckedTripleEquals
//import org.scalatest.{FunSpec, Matchers}
//import wjlow.Main._
//
//class MainSpec extends FunSpec with TypeCheckedTripleEquals with Matchers {
//
//  describe("robotProgram") {
//
//    it("should Place, Move x2, Turn Left, Move and Report") {
//      val inputLines =
//        """
//          |PLACE 1,2,EAST
//          |MOVE
//          |MOVE
//          |LEFT
//          |MOVE
//          |REPORT
//          | """.stripMargin.split("\n").filter(s => !s.trim.isEmpty)
//
//      val states = getProgramStates(inputLines)
//      states(0).outputToPrint should ===(None)
//      states(1).outputToPrint should ===(None)
//      states(2).outputToPrint should ===(None)
//      states(3).outputToPrint should ===(None)
//      states(4).outputToPrint should ===(None)
//      states(5).outputToPrint should ===(None)
//      states(6).outputToPrint should ===(Some("3,3,NORTH"))
//    }
//
//    it("should ignore invalid commands before Place, Report, Move and Report") {
//      val inputLines =
//        """
//          |INVALID
//          |MOVE
//          |REPORT
//          |PLACE 1,2,EAST
//          |REPORT
//          |MOVE
//          |REPORT
//          | """.stripMargin.split("\n").filter(s => !s.isEmpty)
//
//      val states = getProgramStates(inputLines)
//      states(0).outputToPrint should ===(None)
//      states(1).outputToPrint should ===(None)
//      states(2).outputToPrint should ===(None)
//      states(3).outputToPrint should ===(None)
//      states(4).outputToPrint should ===(None)
//      states(5).outputToPrint should ===(Some("1,2,EAST"))
//      states(6).outputToPrint should ===(None)
//      states(7).outputToPrint should ===(Some("2,2,EAST"))
//    }
//
//  }
//
//  private def getProgramStates(inputLines: Array[String]): Array[ProgramState] = {
//    val robotPrograms = inputLines map (line => robotProgram(line)(_))
//    val initialState = ProgramState(None, Nil, None)
//    (robotPrograms scanLeft initialState)((state, f) => f(state))
//  }
//
//}
