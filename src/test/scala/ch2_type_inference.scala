import org.specs._
import org.specs.runner.JUnit4

class TypeInferenceSpecificationTest extends JUnit4(TypeInferenceSpecification)

object TypeInferenceSpecification extends Specification {
  import java.util.Map
  import java.util.HashMap

  "should be able to infer type from type declaration" in {
    val intToStringMap: Map[Int, String] = new HashMap
    // intToStringMap.put("something", 4)
    intToStringMap.put(3, "hello")
    intToStringMap.get(3) must beEqualTo("hello")
  }

  "should be able to infer type from assignment" in {
    val intToStringMap = new HashMap[Int, String]
    // intToStringMap.put("something", 4)
    intToStringMap.put(3, "hello")
    intToStringMap.get(3) must beEqualTo("hello")
  }
}
