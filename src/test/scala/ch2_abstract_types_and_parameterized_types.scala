import org.specs._
import org.specs.runner.JUnit4

class AbstractTypeSpecificationTest extends JUnit4(AbstractTypeSpecification)

object AbstractTypeSpecification extends Specification {
  "StringBulkReader" should {
    "return the source when read" in {
      val stringBulkReader = new StringBulkReader("Hello")
      stringBulkReader.read must_== "Hello"
    }
  }

  "FileBulkReader" should {
    "read whole files..."
  }
}
