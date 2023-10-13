package splitter

import cats.effect.testing.scalatest.AsyncIOSpec
import Domain.Errors.NonPositiveLimit
import Domain.Limit
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

class IntoLinesSplitterSpec
    extends AsyncWordSpec
    with AsyncIOSpec
    with Matchers {

  private val intoWordsSplitter = new IntoWordsSplitter
  val intoLinesSplitter = new IntoLinesSplitter(intoWordsSplitter)

  "creating limit" when {
    "limit is less than 1" should {
      "return error" in {
        Limit(0).attempt.map(err =>
          err.swap.toOption.get shouldBe an[NonPositiveLimit]
        )

        Limit(-1).attempt.map(err =>
          err.swap.toOption.get shouldBe an[NonPositiveLimit]
        )
      }
    }
  }

  "splitting into lines" when {
    "there is nothing to split" should {
      "return empty lines list" in {
        val text = ""
        val limit = Limit(10)

        limit.asserting { l =>
          intoLinesSplitter.splitIntoLines(text, l) shouldEqual List.empty
        }
      }
    }

    "there is a word to split" should {
      "keep that word intact" in {
        val text = "word"
        val limit = Limit(2)

        limit.asserting { l =>
          intoLinesSplitter.splitIntoLines(text, l) shouldEqual List(
            "word"
          )
        }
      }
    }

    "there are multiple words and each should fit in one line" should {
      "split the words into separate lines" in {
        val text = "word1 word2"
        val limit = Limit(4)

        limit.asserting { l =>
          intoLinesSplitter.splitIntoLines(text, l) shouldEqual List(
            "word1",
            "word2"
          )
        }
      }
    }

    "there are multiple words that should fit in one line" should {
      "keep words that fit into line in one line" in {
        val text = "word1 word2 word3"
        val limit = Limit(11)

        limit.asserting { l =>
          intoLinesSplitter.splitIntoLines(text, l) shouldEqual List(
            "word1 word2",
            "word3"
          )
        }
      }
    }

    "there are multiple spaces between words" should {
      "keep spaces if words are not split" in {
        val text = "word1  word2"
        val limit = Limit(20)

        limit.asserting { l =>
          intoLinesSplitter.splitIntoLines(text, l) shouldEqual List(
            "word1  word2"
          )
        }
      }
    }

    "there are multiple spaces between words" should {
      "keep additional spaces if words are split into separate lines" in {
        val text = "word1  w2"
        val limit = Limit(5)

        limit.asserting { l =>
          intoLinesSplitter.splitIntoLines(text, l) shouldEqual List(
            "word1",
            " w2"
          )
        }
      }
    }

    "there is longer text to be split" should {
      "correctly split it into lines" in {
        val text =
          """In 1991, while studying computer science at University of Helsinki, Linus Torvalds began a project that later became the Linux kernel. He wrote the program specifically for the hardware he was using and independent of an operating system because he wanted to use the functions of his new PC with an 80386 processor. Development was done on MINIX using the GNU C Compiler."""
        val limit = Limit(40)

        limit.asserting { l =>
          intoLinesSplitter.splitIntoLines(text, l) shouldEqual List(
            "In 1991, while studying computer science",
            "at University of Helsinki, Linus",
            "Torvalds began a project that later",
            "became the Linux kernel. He wrote the",
            "program specifically for the hardware he",
            "was using and independent of an",
            "operating system because he wanted to",
            "use the functions of his new PC with an",
            "80386 processor. Development was done on",
            "MINIX using the GNU C Compiler."
          )
        }
      }
    }
  }

}
