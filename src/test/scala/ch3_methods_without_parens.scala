import org.specs._
import org.specs.runner.JUnit4

class OperatorPrecedenceSpecificationTest extends JUnit4(OperatorPrecedenceSpecification)

object OperatorPrecedenceSpecification extends Specification {
  List(1,2,3).size must_== 3

  def isEven(n: Int) = (n % 2) == 0

  isEven(4) must beTrue
  isEven(3) must beFalse

  List(1,2,3,4,5,6) filter isEven must_== List(2,4,6)
  List(1,2,3,4) filter isEven foreach println

  (2.0 * 4.0 / 5.0 * 5.0) must_== 8.0

  val list = List(1,2,3)
  'a' :: list must_== List('a', 1, 2, 3)

  List(4,5,6) ++ list must_== List(4,5,6,1,2,3)
  'a' :: List(4,5,6) ++ list must_== List('a',4,5,6,1,2,3)
  'a' :: 'b' :: list must_== List('a', 'b', 1, 2, 3)
}
