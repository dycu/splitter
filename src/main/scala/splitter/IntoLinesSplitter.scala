package splitter

import splitter.Domain.Limit

import scala.annotation.tailrec

class IntoLinesSplitter(intoWordsSplitter: IntoWordsSplitter) {

  private val SpaceLength = 1

  def splitIntoLines(text: String, limit: Limit): List[String] = {
    val wordLengths = intoWordsSplitter.countWordLengths(text)

    @tailrec
    def fold(
        wordLengths: List[(String, Int)],
        out: List[String]
    ): List[String] = {
      wordLengths match {
        case (line, lineLength) :: (word, wordLength) :: restOfWords
            if twoWordsSeparatedLength(lineLength, wordLength) <= limit.value =>
          fold(
            (
              line + ' ' + word,
              twoWordsSeparatedLength(lineLength, wordLength)
            ) :: restOfWords,
            out
          )
        case (line, lineLength) :: (word, wordLength) :: restOfWords
            if twoWordsSeparatedLength(lineLength, wordLength) > limit.value =>
          fold((word, wordLength) :: restOfWords, line :: out)
        case (line, _) :: Nil => line :: out
        case _                => out
      }
    }

    fold(wordLengths, List.empty).reverse
  }

  private def twoWordsSeparatedLength(word1Length: Int, word2Length: Int) =
    word1Length + word2Length + SpaceLength
}
