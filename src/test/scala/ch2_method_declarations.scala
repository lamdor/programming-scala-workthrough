import org.specs._
import org.specs.runner.JUnit4

class StringUtilsSpecificationTest extends JUnit4(StringUtilSpecification)

object StringUtilSpecification extends Specification {
  "joiner joins a list of strings together with a sepaator" in {
    StringUtils.joiner(List("A", "B", "C"), ",") must beEqualTo("A,B,C")
  }

  "joiner joins a list of strings together defaulted with a space" in {
    StringUtils.joiner(List("A", "B", "C")) must beEqualTo("A B C")
  }
}


class FactorialSpecificationTest extends JUnit4(FactorialSpecification)

object FactorialSpecification extends Specification {
  import Factorial._
  "factorial should compute the factorial" in {
    factorial(0) must beEqualTo(1)
    factorial(1) must beEqualTo(1)
    factorial(2) must beEqualTo(2)
    factorial(3) must beEqualTo(6)
    factorial(4) must beEqualTo(24)
  }
}

class CountToSpecificationTest extends JUnit4(CountToSpecification)

object CountToSpecification extends Specification {
  "countTo should return a range up to the number" in {
    CountTo.countTo(5) must beEqualTo(List(1,2,3,4,5))
  }
}
