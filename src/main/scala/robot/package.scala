import cats.data.Writer
import org.atnos.eff._
import cats.data.State
import robot.Robot._

package object robot {

  sealed trait AppError
  case object ParseError extends AppError

  object AppError {
    def parseError: AppError = ParseError
  }

  type _logger[R] = Writer[String, ?] |= R

  type _err[R] = Either[AppError, ?] |= R

  type _robotState[R] = State[Option[ToyRobot], ?] |= R

}
