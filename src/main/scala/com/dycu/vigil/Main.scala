package com.dycu.vigil

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {

  def splitInLines(text: String, limit: Int): List[String] = {
    val wordLengths = text.split(" ").map(w => (w, w.length)).toList
    println(wordLengths)

    def fold(
        wordLengths: List[(String, Int)],
        out: List[String]
    ): List[String] = {
      wordLengths match {
        case (sentence, sentenceLength) :: (word, wordLength) :: restOfWords
            if sentenceLength + wordLength + 1 <= limit =>
          fold(
            (
              sentence + ' ' + word,
              sentenceLength + 1 + wordLength
            ) :: restOfWords,
            out
          )
        case (sentence, sentenceLength) :: (word, wordLength) :: restOfWords
            if sentenceLength + wordLength + 1 > limit =>
          fold((word, wordLength) :: restOfWords, sentence :: out)
        case (sentence, _) :: Nil => sentence :: out
        case Nil                  => out
      }
    }

    fold(wordLengths, List.empty).reverse
  }

  // This is your new "main"!
  def run: IO[Unit] =
    IO {
      val value = splitInLines(
        """In 1991, while studying computer science at University of Helsinki, Linus Torvalds began a project that later became the Linux kernel. He wrote the program specifically for the hardware he was using and independent of an operating system because he wanted to use the functions of his new PC with an 80386 processor. Development was done on MINIX using the GNU C Compiler.
          |""".stripMargin,
        40
      )
      println(value.size)
      value.foreach(println)
    }
}
