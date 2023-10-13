package com.dycu.vigil

class IntoWordsSplitter {

  def countWordLengths(text: String): List[(String, Int)] = {
    if (text.nonEmpty) text.split(' ').map(w => (w, w.length)).toList.map {
      case ("", _) =>
        (" ", 1)
      case other => other
    }
    else List.empty
  }

}
