import org.specs._

object ForComprehensionSpecification extends Specification {
  val dogBreeds = List("Doberman", "Yorkshire Terrier",
                       "Collie", "Labrador",
                       "Scottish Terrier", "Poodle",
                       "Great Dane")

  "the for comprehension" should {
    "iterate through a list" >> {
      var result = List[String]()
      for (breed <- dogBreeds) {
        result ::= breed
        println(breed)
      }
      result must beEqualTo(dogBreeds.reverse)
    }

    "filter a list" >> {
      var result = List[String]()
      for (breed <- dogBreeds
           if breed.contains("Terrier"))
        result ::= breed

      result must beEqualTo(List("Scottish Terrier", "Yorkshire Terrier"))
    }

    "handle multiple filters" >> {
      var result = List[String]()
      for (breed <- dogBreeds
           if breed.contains("Terrier");
           if !breed.startsWith("Yorkshire"))
        result ::= breed
      result must beEqualTo(List("Scottish Terrier"))
    }

    "yield the entry creating a list (instead of appending)" >> {
      val filteredBreeds = for { breed <- dogBreeds
                                if breed.contains("Terrier")
                                if !breed.startsWith("Yorkshire")
                              }  yield breed
      filteredBreeds must beEqualTo(List("Scottish Terrier"))
    }

    "allow variables in it's scope (expanded scope)" >> {
      val filteredBreeds = for { breed <- dogBreeds
                                upcasedBreed = breed.toUpperCase
                                if breed.contains("Terrier")
                              } yield upcasedBreed
      filteredBreeds must beEqualTo(List("YORKSHIRE TERRIER", "SCOTTISH TERRIER"))
    }

    "iterate through multiple lists" >> {
      for (x <- 1 to 5; y <- 1 to 3) {
        println("x=" + x + "; y=" + y)
      }
    }

  }
}
