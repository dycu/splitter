package com.dycu.vigil

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class IntoWordsSplitterSpec extends AnyWordSpec with Matchers {

  val intoWordsSplitter = new IntoWordsSplitter

  "counting word lengths" when {
    "there is no words" should {
      "return empty list" in {
        intoWordsSplitter.countWordLengths("") shouldEqual List.empty
      }
    }

    "there are multiple empty spaces" should {
      "keep them as word" in {
        intoWordsSplitter.countWordLengths(("  ")) shouldEqual List.empty
      }
    }

    "there is a word" should {
      "return it with the correct size" in {
        intoWordsSplitter.countWordLengths("word") shouldEqual List(
          ("word", 4)
        )
      }
    }

    "there is a word with spaces at the end" should {
      "return it with the correct size ignoring the following spaces" in {
        intoWordsSplitter.countWordLengths("word         ") shouldEqual List(
          ("word", 4)
        )
      }
    }

    "there is a word with empty spaces" should {
      "return it with the correct size keeping the spaces" in {
        intoWordsSplitter.countWordLengths("  word    ") shouldEqual List(
          (" ", 1),
          (" ", 1),
          ("word", 4)
        )
      }
    }
  }
}
