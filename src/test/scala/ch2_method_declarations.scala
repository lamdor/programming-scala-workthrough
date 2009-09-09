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
