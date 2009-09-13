import org.specs._

object otherLoopConstructs extends Specification {
  "Scala" should {
    "have a while loop" >> {
      var results = List[Int]()
      var i = 0
      while (i < 5) {
        results ::= i
        i += 1
      }
      results must_== List(4,3,2,1,0)
    }

    "have a do-while loop" >> {
      var results = List[Int]()
      var i = 0
      do {
        results ::= i
        i += 1
      } while (i < 5)

      results must_== List(4,3,2,1,0)
    }

    "be able to loop over a range" >> {
      var results = List[Int]()
      for (i <- 0 to 4) results ::= i

      results must_== List(4,3,2,1,0)
    }

    "be able to loop over a range exclusively" >> {
      var results = List[Int]()
      for (i <- 0 until 4) results ::= i

      results must_== List(3,2,1,0)
    }
  }
}
