import org.specs._

object TraditionalConcurrencySpecification extends Specification {
  "Scala threads" should {
    "be created using new Thread" in {
      var called = false
      new Thread { Thread.sleep(200); called = true }
      Thread.sleep(250)
      called must beTrue
    }

    "be created using scala.concurrent.ops.spawn" in {
      import scala.concurrent.ops._

      var called = false
      spawn { Thread.sleep(200); called = true }
      Thread.sleep(250)
      called must beTrue
    }

    "identify the current thread using currentThread" in {
      println("hello from thread:" + currentThread.getId)

      true
    }

    "can use java.util.concurrent" in {
      import java.util.concurrent._

      class ThreadIdentifier extends Runnable {
        def run {
          println("hello from thread:" + currentThread.getId)
        }
      }

      val threadPool = Executors.newFixedThreadPool(10)
      for (i <- 1 to 10)
        threadPool.execute(new ThreadIdentifier)
    }
  }
}
