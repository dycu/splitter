package com.dycu.vigil

class IntoWordsSplitter {

  def countWordLengths(text: String): List[(String, Int)] =
    text.split(" ").map(w => (w, w.length)).toList

}
