object CountTo {
  def countTo(n: Int): List[Int] = {
    var result = List[Int]()
    def count(i: Int): Unit = {
      if (i > 0) {
        result ::= i
        count(i - 1)
      }
    }
    count(n)
    result
  }
}
