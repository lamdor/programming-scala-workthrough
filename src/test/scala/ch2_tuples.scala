import org.specs._
import org.specs.runner.JUnit4

class TuplesSpecificationTest extends JUnit4(TuplesSpecification)

object TuplesSpecification extends Specification {
  "should be able to create tuples" in {
    def tuplenator(x1: Any, x2: Any, x3: Any) = (x1, x2, x3)
    val t1 = tuplenator("Hello", 1, 2.3)
    t1._1 must beEqualTo("Hello")
    t1._2 must beEqualTo(1)
    t1._3 must beEqualTo(2.3)

    val t2 = 1 -> 2
    t2._1 must beEqualTo(1)
    t2._2 must beEqualTo(2)

    val t3 = Tuple2(1,2)
    t3._1 must beEqualTo(1)
    t3._2 must beEqualTo(2)

    // val t4 = Tuple2(1,2,3)
    val t4 = Tuple3(1,2,3)
    t4._1 must beEqualTo(1)
    t4._2 must beEqualTo(2)
    t4._3 must beEqualTo(3)

    val t5 = Pair(1,2)
    t5._1 must beEqualTo(1)
    t5._2 must beEqualTo(2)
  }
}
