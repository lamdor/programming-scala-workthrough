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

  "should need a type for a val/var unless it is assigned" in {
    // val something
    val something: String = null
    something must beNull

    val something2 = "abcd"
    something2 must beEqualTo("abcd")
  }

  "should always need a type for parameters" in {
    // def upcase(s) = s.toUpperCase
    def upcase(s: String) = s.toUpperCase()
    upcase("") must beEqualTo("")
    upcase("aBCd") must beEqualTo("ABCD")
  }

  "should need a return type if explicitly calling return" in {
    //def upcase(s: String) = {
    def upcase(s: String): String = {
      if (s.length == 0)
        return ""
      else
        s.toUpperCase()
    }
    upcase("") must beEqualTo("")
    upcase("aBCd") must beEqualTo("ABCD")
  }

  "should need a return type if the method is recursive" in {
    def factorial(i: Int) = {
      // def fact(i: Int, accumulator: Int) = {
      def fact(i: Int, accumulator: Int): Int = {
        if (i <= 1)
          accumulator
        else
          fact(i - 1, i * accumulator)
      }
      fact(i, 1)
    }
    factorial(3) must beEqualTo(6)
  }

  "should need a return type if the method is overloaded (calling method needs return type)" in {
    object Joiner {
      def joiner(strings: List[String], separator: String) = strings.mkString(separator)
      // def joiner(strings: List[String]) = joiner(strings, " ")
      def joiner(strings: List[String]): String = joiner(strings, " ")
    }

    Joiner.joiner(List("a", "b", "c"), ",") must beEqualTo("a,b,c")
    Joiner.joiner(List("a", "b", "c")) must beEqualTo("a b c")
  }

  "should need a return type when the type needs to be more general" in {
    def makeList(strings: String*) = {
      if (strings.length == 0)
        List()
      else
        strings.toList
    }

    makeList() must beEqualTo(List())
    makeList("a", "b", "c") must beEqualTo(List("a", "b", "c"))

    val someList: List[String] = makeList()
    someList must beEqualTo(List())
  }

  "should return Unit (void/nil) if it is a procedure (with side effects)" in {
    def double(i: Int) { 2 * i }
    double(2) must beEqualTo(())

    // notice now the equals sign
    def double2(i: Int) = { 2 * i }
    double2(2) must beEqualTo(4)
  }
}

class StringUtilsToCollectionSpecificationTest extends JUnit4(StringUtilsToCollectionSpecification)

object StringUtilsToCollectionSpecification extends Specification {
  "should split a string apart" in {
    val result = StringUtils.toCollection("this is a string")
    result.length must beEqualTo(4)
    result(0) must beEqualTo("this")
    result(1) must beEqualTo("is")
    result(2) must beEqualTo("a")
    result(3) must beEqualTo("string")
  }
}

class MapToNothingSpecificationTest extends JUnit4(MapToNothingSpecification)

object MapToNothingSpecification extends Specification {
  "should not allow things to be added to it" in {
    val map = Map()
    // map.put("abc", 1) // value put is not a member of scala.collection.immutable.Map[Nothing,Nothing]

    map must beEqualTo(Map[Nothing, Nothing]())
  }
}
