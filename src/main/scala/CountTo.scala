object CountTo {
  def countTo(n: Int): List[Int] = {
    var result = List[Int]()
    def count(i: Int): Unit = {
      if (i <= n) {
        result = i :: result
        count(i + 1)
      }
    }
    count(1)
    result reverse
  }
}
