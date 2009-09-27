import org.specs._

object ContainerMethodsSpecification extends Specification {
  val stateCapitals = Map("Alabama" -> "Montgomery",
                          "Alaska" -> "Juneau",
                          "Wyoming" -> "Cheyenne")

  "Tranversal" should {
    "be accomplished through the foreach method" in {
      val list = List(1,2,3)
      var results:List[Int] = Nil

      list foreach { i => results ::= i }
      results must_== List(3,2,1)

      val map = Map("a" -> 1, "b" -> 2)
      var mapResults: List[String] = Nil
      map foreach { kv => mapResults ::= kv._1 }
      mapResults.length must_== 2
      mapResults must containAll(List("a", "b"))
    }
  }

  "Mapping" should {
    "apply the function supplied to each element with map" in {
      val lengths = stateCapitals.map { kv => (kv._1, kv._2.length)}
      lengths must contain(("Alaska", 6))
      lengths must contain(("Alabama", 10))
      lengths must contain(("Wyoming", 8))

      val lengthsMap = Map[String, Int]() ++ lengths
      lengthsMap must havePair("Alaska" -> 6)
      lengthsMap must havePair("Alabama" -> 10)
      lengthsMap must havePair("Wyoming" -> 8)
    }

    "apply the function and concatenate the results together with flatMap" in {
      val l1 = List(1,List(2,3),4,Nil,5,6)

      def flatten(list: List[_]): List[_] = list flatMap {
        case head :: tail => head :: flatten(tail)
        case Nil => Nil
        case x => List(x)
      }

      val results = flatten(l1)

      results must beEqualTo(List(1,2,3,4,5,6))
    }
  }

  "Filtering" should {
    "remove elements which do not pass the function supplied" in {
      val results = stateCapitals.filter { kv => kv._1.startsWith("A") }

      results.size must_== 2
      results must haveKey("Alaska")
      results must haveKey("Alabama")
    }
  }

  "Reducing" should {
    "start witht the first element and work like inject" in {
      val result = List(1,2,3,4,5) reduceLeft { _ + _ }
      result must_== 15
    }

    "should throw an error if an empty iterable is tried" in {
      val list = List[Int]()
      // list reduceLeft (_ + _) // java.lang.UnsupportedOperationException: Nil.reduceLeft

      list
    }

    "have tail cail optimization for the left form" in {
      val result = (1 to 1000000) reduceLeft { _ + _  }
      // (1 to 1000000) reduceRight { _ + _ } // java.lang.StackOverflowError

      result must_== 1784293664
    }
  }

  "Folding" should {
    "need a user supplied value to start and then reduce" in {
      val result = List(1,2,3,4,5,6).foldLeft(10) (_ * _)
      result must_== 7200
    }

    "just return the seed value when given an empty iterable" in {
      val list = List[Int]()
      val result = list.foldLeft(10) { _ * _ }
      result must_== 10
    }

    "be able to be like map 1" in {
      val numbers = List(1,2,3,4,5)
      val result = numbers.foldLeft(List[Int]()) {
        (list, x) => (2 * x) :: list
      }.reverse
      result must_== List(2,4,6,8,10)
    }

    "be able to be like map 2" in {
      val numbers = List(1,2,3,4,5)
      val result = numbers.foldRight(List[Int]()) {
        (x, list) => (2 * x) :: list
      }
      result must_== List(2,4,6,8,10)
    }
  }

  "Option classes" should {
    "have the same container methods as well" in {
      val someNumber = Some(5)
      val noneNumber = None

      val things = List(someNumber, noneNumber)
      val results = things.flatMap {
        option => option.map(_ * 5)
      }
      results must_== List(25)
    }
  }

}
