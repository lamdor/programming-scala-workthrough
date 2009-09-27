import org.specs._

object CurryingSpecification extends Specification {
  "Curried Functions" should {
    "be able to be created using multiple parameter lists" in {
      def cat(s1: String)(s2: String) = s1 + s2
      cat("abc")("def") must_== "abcdef"

      val abcCat = cat("abc") _
      abcCat("def") must_== "abcdef"
    }

    "be equivalent to having a function return a function" in {
      def cat(s1: String) = (s2: String) => s1 + s2
      cat("abc")("def") must_== "abcdef"

      val abcCat = cat("abc") // notice no trailing "_"
      abcCat("def") must_== "abcdef"
    }

    "be created by using Function.curried on an existing method" in {
      def cat(s1: String, s2: String) = s1 + s2
      val curryCat = Function.curried(cat _)
      curryCat("abc")("def") must_== cat("abc", "def")

      val abcCat = curryCat("abc") // notice no trailing "_"
      abcCat("def") must_== "abcdef"

      val partialCurryCat = curryCat("abc")(_)
      partialCurryCat("def") must_== "abcdef"
    }

    "be use to create multipler functions" in {
      def multiplier(i: Int)(factor: Int) = i * factor
      val byFive = multiplier(5) _
      val byTen = multiplier(10) _
      byFive(3) must_== 15
      byTen(2) must_== 20
    }
  }
}
