import org.specs._

object PredefSpecification extends Specification {
  "Scala Predef" should {
    "have 4 ways to create a 2-tuple" in {
      val expected = ("Hello", 3.14)
      (Pair("Hello", 3.14) == expected) must beTrue
      (Tuple2("Hello", 3.14) == expected) must beTrue
      (("Hello" -> 3.14) == expected) must beTrue
    }
  }
}
