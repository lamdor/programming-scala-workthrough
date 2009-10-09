import org.specs._

object InfiniteSequencesSpecification extends Specification {
  "Reference fibonacci sequence" should {
    "generate the nth fibonacci sequence" in {
      def fib(n: Int): Int = n match {
        case 0 | 1 => 1
        case _ => fib(n-1) + fib(n-2)
      }

      (0 to 4).map(fib).toList mustEqual List(1,1,2,3,5)
    }
  }
  "Scala streams" should {
    "generate a lazy sequqnce" in {
      def from(n: Int): Stream[Int] = Stream.cons(n, from(n+1))

      lazy val ints = from(0)
      lazy val odds = ints.filter(_ % 2 == 1)
      lazy val evens = ints.filter(_ % 2 == 0)

      odds.take(5).toList mustEqual List(1,3,5,7,9)
      evens.take(5).toList mustEqual List(0,2,4,6,8)
    }

    "generate the fibonacci sequence" in {
      lazy val fib: Stream[Int] =
        Stream.cons(0, Stream.cons(1, fib.zip(fib.tail).map(p => p._1 + p._2)))

      fib.take(6).toList mustEqual List(0,1,1,2,3,5)
    }
  }
}
