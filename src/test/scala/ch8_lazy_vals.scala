import org.specs._

object LazyValsSpecification extends Specification {
  "Lazy vals" should {
    "evaluate only when needed" in {
      trait AbstractT {
        val value: Int
        lazy val inverse = { println("computing inverse"); 1.0 / value }
      }

      val cWithT = new AbstractT {
        val value = 10
      }

      println("I have cWithT")

      cWithT.inverse must_== 0.1
    }
  }
}
