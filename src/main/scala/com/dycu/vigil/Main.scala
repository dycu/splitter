package com.dycu.vigil

import cats.effect.{IO, IOApp}
import com.dycu.vigil.Domain.Limit

object Main extends IOApp.Simple {

  private val intoWordsSplitter = new IntoWordsSplitter
  val intoLinesSplitter = new IntoLinesSplitter(intoWordsSplitter)

  def run: IO[Unit] = for {
    limit <- Limit(40)
    result <- IO {
      intoLinesSplitter.splitIntoLines(
        """In 1991, while studying computer science at University of Helsinki, Linus Torvalds began a project that later became the Linux kernel. He wrote the program specifically for the hardware he was using and independent of an operating system because he wanted to use the functions of his new PC with an 80386 processor. Development was done on MINIX using the GNU C Compiler.
          |""".stripMargin,
        limit
      )
    }
    _ <- IO(println(result.size))
    _ <- IO(result.foreach(println))
  } yield ()
}
