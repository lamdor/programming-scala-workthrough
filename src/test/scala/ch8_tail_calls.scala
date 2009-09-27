import org.specs._

object TailCallsSpecification extends Specification {
  "Scala" should {
    "blow up with a stack overflow" in {
      def factorial(n: Int): Int = n match {
        case _ if n == 1 => n
        case _ => n * factorial(n - 1)
      }

      // factorial(1000000) // java.lang.StackOverflowError

      true
    }
    "avert stack overflow by not holding the tail" in {
      def factorial(n: BigInt): BigInt = {
        def fact(i: BigInt, acc: BigInt): BigInt = i match {
          case _ if i == 1 => acc
          case _ => fact(i - 1, i * acc)
        }
        fact(n, 1)
      }

      factorial(4) must_== 24
    }
  }
}
