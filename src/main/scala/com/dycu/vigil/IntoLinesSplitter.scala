package com.dycu.vigil

import com.dycu.vigil.Domain.Limit

import scala.annotation.tailrec

class IntoLinesSplitter(intoWordsSplitter: IntoWordsSplitter) {

  def splitIntoLines(text: String, limit: Limit): List[String] = {
    val wordLengths = intoWordsSplitter.countWordLengths(text)

    @tailrec
    def fold(
        wordLengths: List[(String, Int)],
        out: List[String]
    ): List[String] = {
      wordLengths match {
        case (line, lineLength) :: (word, wordLength) :: restOfWords
            if lineLength + wordLength + 1 <= limit.value =>
          fold(
            (
              line + ' ' + word,
              lineLength + 1 + wordLength
            ) :: restOfWords,
            out
          )
        case (line, lineLength) :: (word, wordLength) :: restOfWords
            if lineLength + wordLength + 1 > limit.value =>
          fold((word, wordLength) :: restOfWords, line :: out)
        case (line, _) :: Nil => line :: out
        case Nil              => out
      }
    }

    fold(wordLengths, List.empty).reverse
  }
}
