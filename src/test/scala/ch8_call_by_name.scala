import org.specs._

object CallByNameSpecification extends Specification {
  "the old way" should {
    "force you to use parentheses" in {
      def whileAwesome(conditional: () => Boolean)(f: () => Unit) {
        if (conditional()) {
          f()
          whileAwesome(conditional)(f)
        }
      }

      var count = 0
      whileAwesome(() => {count < 5}) { () =>
        println("Still awesome")
        count += 1
      }
      count must_== 5
    }
  }

  "call by name" should {
    "allow you to omit the parens" in {
      def whileAwesome(conditional: => Boolean)(f: => Unit) {
        if (conditional) {
          f
          whileAwesome(conditional)(f)
        }
      }

      var count = 0
      whileAwesome(count < 5) {
        println("awesome")
        count += 1
      }
      count must_== 5
    }
  }
}
