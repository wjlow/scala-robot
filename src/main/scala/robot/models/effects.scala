package robot.models

import cats.data._
import org.atnos.eff._

object effects {

  type _logger[R] = Writer[String, ?] |= R

  type _err[R] = Either[AppError, ?] |= R

  type _robotState[R] = State[Option[Robot], ?] |= R

}
