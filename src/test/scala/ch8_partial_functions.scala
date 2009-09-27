import org.specs._

object PartialFunctionSpecification extends Specification {
  "Partial functions" should {
    def concatUpper(s1: String, s2: String) = (s1 + " " + s2).toUpperCase

    "allow us to copy a function" in {
      val c = concatUpper _
      c("abc", "def") must_== "ABC DEF"
    }

    "allow us to set wildcards for both parameters" in {
      val c = concatUpper(_: String, _: String)
      c("abc", "def") must_== "ABC DEF"
    }

    "allow us to set one of the parameters" in {
      val c = concatUpper("abc", _: String)
      c("def") must_== "ABC DEF"
    }

    "allow fallbacks through orElse" in {
      val truthier: PartialFunction[Boolean, String] = { case true => "truthful"}
      val fallback: PartialFunction[Boolean, String] = { case x => "sketchy"}

      val tester = truthier orElse fallback

      tester(true) must_== "truthful"
      tester(false) must_== "sketchy"
    }
  }

  "Case statements" should {
    "be expanded to partial functions" in {
      val pantsTest: PartialFunction[String, String] = {
        case "pants" => "yes, we have pants!"
      }
      pantsTest.isDefinedAt("pants") must beTrue
      pantsTest.isDefinedAt("shorts") must beFalse
    }
  }
}
