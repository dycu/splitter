package splitter

class IntoWordsSplitter {

  def countWordLengths(text: String): List[(String, Int)] = {
    val trimmed = text.trim

    if (trimmed.nonEmpty)
      trimmed.split(" ").map(w => (w, w.length)).toList
    else List.empty
  }

}
