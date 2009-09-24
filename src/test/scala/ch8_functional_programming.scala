import org.specs._

object FunctionalProgrammingSpecification extends Specification {
  "Scala functional programming" should {
    "allow functions to take in other functions" in {
      List(1,2,3,4).map(_ * 2) must_== List(2,4,6,8)
      List(1,2,3,4) reduceLeft (_ + _) must_== 10
    }

    "allow functions to be assigned to a variable" in {
      var factor = 3
      val multipler = (i:Int) => i * factor

      List(1,2,3,4).map(multipler) must_== List(3,6,9,12)

      factor = 4
      List(1,2,3,4).map(multipler) must_== List(4,8,12,16)
    }
  }
}
