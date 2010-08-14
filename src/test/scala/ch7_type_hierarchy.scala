import org.specs._

object TypeHierarchySpecification extends Specification {
  "Either class" should {
      "return either left or right types" in {
        def oneOrTheOther(i: Int): Either[Int, String] = {
          if (i < 5)
            Left(10)
          else
            Right("i'm the right")
        }

        oneOrTheOther(3) match {
          case Left(i) => i must_== 10
          case Right(s) => fail("Not the right side")
          case _ => fail("didn't match")
        }
        oneOrTheOther(6) match {
          case Left(i) => fail("Not the left side")
          case Right(s) => s must_== "i'm the right"
          case _ => fail("didn't match")
        }
      }
  }

  "Iterable trait" should {
    "allow subclasses to iterate through" in {
      val myIterable = new Iterable[Int] {
        def iterator =  List(1,2,3).iterator
      }

      myIterable.isEmpty must beFalse
      myIterable.find(i => i % 2 == 0) must beSome[Int].which(_ == 2)
    }
  }

}
