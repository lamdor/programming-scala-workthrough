import org.specs._
import org.specs.runner.JUnit4

class DefineVariablesTest extends JUnit4(defineVariablesSpec)

object defineVariablesSpec extends Specification {
  "val does not allow reassignment" in {
    val array: Array[String] = new Array(5)
    // array = new Array(2)
    array
  }

  "val does allow object to be modified however" in {
    val array: Array[String] = new Array(5)
    array(0) = "Hello"

    array(0) must beEqualTo("Hello")
  }

  "var allows object to be redefined" in {
    var stockPrice = 100.0
    stockPrice must beEqualTo(100.0)

    stockPrice = 10.0
    stockPrice must beEqualTo(10.0)
  }
}
