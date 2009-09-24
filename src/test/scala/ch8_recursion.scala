import org.specs._

object RecursionSpecification extends Specification {
  "Recursion" should {
    "be better than imperative code" in {
      def factorial(n: Int): Int = {
        var result = 1
        for (i <- 2 to n)
          result *= i
        result
      }
      factorial(4) must_== 24
    }
    "allow a function to call itself" in {
      def factorial(n: Int): Int = n match {
        case _ if n == 1 => n
        case _ => n * factorial(n - 1)
      }
      factorial(4) must_== 24
    }
  }
}
