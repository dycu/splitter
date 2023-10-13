package com.dycu.vigil

import cats.effect.IO
import com.dycu.vigil.Domain.Errors.NonPositiveLimit

object Domain {

  case class Limit(value: Int) extends AnyVal

  object Limit {
    def apply(value: Int): IO[Limit] = {
      if (value > 0) IO(new Limit(value))
      else IO.raiseError(new NonPositiveLimit(value))
    }
  }

  object Errors {
    case class NonPositiveLimit(limit: Int) extends RuntimeException
  }
}
