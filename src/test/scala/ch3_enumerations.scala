import org.specs._

object EnumerationsSpecification extends Specification {
  "Scala Enumerations" should {

    object Breed extends Enumeration {
      val doberman = Value("Doberman Pinscher")
      val yorkie = Value("Yorkshire Terrier")
      val scottie = Value("Scottish Terrier")
      val dane = Value("Great Dane")
      val portie = Value("Portuguese Water Dog")
    }

    "have id's" >> {
      println("Id\tBreed")
      for (breed <- Breed) println(breed.id + "\t" + breed)
    }

    "be able to be filtered" >> {
      println("Just Terriers")
      println("-" * 20)
      Breed.filter(_.toString.endsWith("Terrier")).foreach(println)
    }

    "not have to human readable toStrings" >> {
      object WeekDay extends Enumeration {
        type WeekDay = Value
        val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
      }

      import WeekDay._

      def isWorkingDay(day: WeekDay) = ! (day == Sat || day == Sun)

      WeekDay filter isWorkingDay foreach println
    }
  }
}
