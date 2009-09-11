import org.specs._
import org.specs.runner.JUnit4

class OperatorPrecedenceSpecificationTest extends JUnit4(OperatorPrecedenceSpecification)

object OperatorPrecedenceSpecification extends Specification {
  (2.0 * 4.0 / 5.0 * 5.0) must_== 8.0

  val list = List(1,2,3)
  'a' :: list must_== List('a', 1, 2, 3)

  List(4,5,6) ++ list must_== List(4,5,6,1,2,3)
  'a' :: List(4,5,6) ++ list must_== List('a',4,5,6,1,2,3)
}
