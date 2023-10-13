package com.dycu.vigil

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {

  private val intoWordsSplitter = new IntoWordsSplitter
  val intoLinesSplitter = new IntoLinesSplitter(intoWordsSplitter)

  // This is your new "main"!
  def run: IO[Unit] =
    IO {
      val value = intoLinesSplitter.splitIntoLines(
        """In 1991, while studying computer science at University of Helsinki, Linus Torvalds began a project that later became the Linux kernel. He wrote the program specifically for the hardware he was using and independent of an operating system because he wanted to use the functions of his new PC with an 80386 processor. Development was done on MINIX using the GNU C Compiler.
          |""".stripMargin,
        40
      )
      println(value.size)
      value.foreach(println)
    }
}
