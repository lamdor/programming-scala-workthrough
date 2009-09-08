import org.specs._
import org.specs.runner.JUnit4

class UpperSpecificationTest extends JUnit4(UpperSpecification)

object UpperSpecification extends Specification {
  "Upper#upper uppercases words" in {
    val up = new Upper
    val results = up.upper("A", "First", "Taste")
    results.length must_== 3
    results must contain("A")
    results must contain("FIRST")
    results must contain("TASTE")
  }
}
