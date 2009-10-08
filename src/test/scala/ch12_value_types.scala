import org.specs._

object ValueTypesSpecification extends Specification {
  "Infix types" should {
    "be left or right" in {
      def attempt(operation: => Boolean): Throwable Either Boolean = try {
        Right(operation)
      } catch {
        case t: Throwable => Left(t)
      }

      attempt { throw new RuntimeException("Expected") } match {
        case Left(t) => t must haveClass[RuntimeException]
        case _ => fail("did not match")
      }
      attempt { true } match {
        case Right(r) => r must beTrue
        case _ => fail("did not match")
      }
      attempt { false } match {
        case Right(r) => r must beFalse
        case _ => fail("did not match")
      }
    }
  }

  "Function Types" should {
    "create a function object" in {
      val capitalizer = (s: String) => s.toUpperCase
      val capitalizer2 = new Function1[String, String] {
        def apply(s: String) = s.toUpperCase
      }

      capitalizer("abc") mustEqual "ABC"
      capitalizer2("abc") mustEqual "ABC"
    }

    "be able to be curried" in {
      val f = (x: Double, y: Double, z: Double) => x * y / z
      val fc = f.curry

      val answer1 = f(3,4,2)
      val answer2 = fc(3)(4)(2)

      answer1 mustEqual answer2
      answer1 mustEqual 6
    }
  }
}
