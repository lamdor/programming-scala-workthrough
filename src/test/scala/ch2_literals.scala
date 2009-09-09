import org.specs._
import org.specs.runner.JUnit4

class LiteralsSpecificationTest extends JUnit4(LiteralsSpecification)

object LiteralsSpecification extends Specification {
  "should be able to do numerical literals" in {
    // val i = 12345678901234567890 // error: integer number too large

    // val b: Byte = 128 // error: type mismatch

    val b: Byte = 127

    0.
    0.1
    3e5
    3.14E-5

    true
  }

  "should be able to do boolean literals" in {
    val b1 = true
    val b2 = false
    b2
  }

  "should be able to do character literals" in {
    var ch1 = 'A'
    var ch2 = '\u0041'

    ch1
  }

  "should be able to do string literals" in {
    var string1 = "This is a string"
    var string2 = "This is \"also\" a string"

    var string3 = """This is a\n
    multiline string"""

    string3
  }

  "should be able to do symbol literals" in {
    val something = 'something
    val something2 = scala.Symbol("Programming Scala")
    something
  }
}
