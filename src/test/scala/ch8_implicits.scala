import org.specs._

object ImplicitsSpecification extends Specification {
  import scala.runtime.RichString

  "Scala implicits" should {
    "already wrap the java.lang.String in a scala.runtime.RichString" in {
      val result: String = "scala".capitalize.reverse
      result must_== "alacS"
    }

    class FancyString(val str: String)

    "be able to wrap an instance in some wrapper" in {
      object FancyString2RichString {
        implicit def fancyString2RichString(f: FancyString) = new RichString(f.str)
      }

      import FancyString2RichString._

      val fs = new FancyString("abc")
      val result: String = fs.capitalize.reverse
      result must_== "cbA"
    }

    "find the implicit function in the current scope" in {
      implicit def fancyString2RichString(f: FancyString) = new RichString(f.str)
      val fs = new FancyString("abc")
      val result: String = fs.capitalize.reverse
      result must_== "cbA"
    }

    "be able to be used in function parameters" in {
      def multipler(i: Int)(implicit factor: Int) = i * factor

      implicit val factor = 2

      multipler(2) must_== 4
      multipler(2)(3) must_== 6
    }
  }
}
