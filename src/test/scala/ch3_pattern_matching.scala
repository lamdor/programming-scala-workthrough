import org.specs._

object PatternMatchingSpecification extends Specification {
  "Scala's pattern matching" should {
    "match simple booleans" >> {
      val bools = List(true, false)
      for (bool <- bools) {
        bool match {
          case true => println("heads")
          case false => println("tails")
          case _ => println("what is this?")
        }
      }
    }

    "be able to use variables in the matching expression" >> {
      import scala.util.Random

      val randomInt = new Random().nextInt(10)
      randomInt match {
        case 7 => println("lucky seven!")
        case otherNumber => println("boo, boring old " + otherNumber)
      }
    }

    "be able to match on type" >> {
      val sundries = List(23, "hello", 8.4, 'q')
      for (sundry <- sundries) {
        sundry match {
          case s: String => println("got String (uppercased): " + s.toUpperCase())
          case i: Int => println("got Int: " + i)
          case f: Float => println("got Float: "+ f)
          case other => println("got something else: " + other)
        }
      }
    }

    "be able to match on a sequence" >> {
      val willWork = List(1,3,23,90)
      val willNotWork = List(4,18,52)
      val empty = List()

      for (l <- List(willWork, willNotWork, empty)) l match {
        case List(_, 3, _, _) => println("Four elements with the second being 3.")
        case List(_*) => println("More than 0 or more elements")
      }
    }

    "be able to use the 'cons' method - :: for sequence matching" >> {
      val willWork = List(1,3,23,90)
      val willNotWork = List(4,18,52)
      val empty = List()

      def processList(l: List[Any]): Unit = l match {
        case head :: tail => {
          format("%s ", head)
          processList(tail)
        }
        case Nil => println("")
      }

      for (l <- List(willWork, willNotWork, empty)) {
        print("List: ")
        processList(l)
      }
    }

    "be able to match on tuples" >> {
      val tupA = ("Good", "Morning")
        val tupB = ("Guten", "Tag!")

          for (tup <- List(tupA, tupB)) {
            tup match {
              case (thingOne, thingTwo) if thingOne == "Good" =>
                println("A two-tuple starting with 'Good'")
              case (thingOne, thingTwo) =>
                println("This has two things: " + thingOne + " and " + thingTwo)
            }
          }
    }

    "be able to match on case classes" >> {
      case class Person(val name: String, val age: Int)

      val alice = new Person("Alice", 25)
      val bob = new Person("Bob", 32)
      val charlie = new Person("Charlie", 52)

      for (p <- List(alice, bob, charlie)) p match {
        case Person("Alice", 25) => println("Hi Alice!")
        case Person("Bob", 32) => println("Hey Bob.")
        case Person(name, age) =>
          println("Who are you, a " + age + " old person named " + name)
      }
    }

    "be able to match on regular expressions" >> {
      val BookExtractorRE = """Book: title=([^,]+),\s+authors=(.+)""".r
      val MagazineExtractorRE = """Magazine: title=([^,]+),\s+issue=(.+)""".r

      val catalog = List("Book: title=Programming Scala, authors=Dean Wampler, Alex Payne",
                         "Magazine: title=The New Yorker, issue=January 2009",
                         "Book: title=War and Peace, authors=Leo Tolstoy",
                         "Magazine: title=The Atlantic, issue=February 2009",
                         "BadData: text=Who put this here??")

      for (item <- catalog) item match {
        case BookExtractorRE(title, authors) =>
          println("Book \"" + title + "\" written by " + authors)
        case MagazineExtractorRE(title, issue) =>
          println("Magazine \"" + title + "\" published in " + issue)
        case entry => println("Unrecognized entry - " + entry)
      }
    }

    "be able to bind nested variables in case classes" >> {
      class Role
      case object Manager extends Role
      case object Developer extends Role

      case class Person(name: String, age: Int, role: Role)

      val alice = new Person("Alice", 25, Developer)
      val bob = new Person("Bob", 32, Manager)
      val charlie = new Person("Charlie", 52, Developer)

      for (item <- Map(1 -> alice, 2 -> bob, 3 -> charlie)) item match {
        case (id, p @ Person(_, _, Manager)) =>
          format("%s is overpaid.\n", p)
        case (id, p @ Person(_, _, _)) =>
          format("%s is underpaid.\n", p)
      }

      // could also be written
      for (item <- Map(1 -> alice, 2 -> bob, 3 -> charlie)) item match {
        case (id, p) => p match {
          case Person(_, _, Manager) => format("%s is overpaid.\n", p)
          case Person(_, _, _) => format("%s is underpaid.\n", p)
        }
      }
    }

    "be able to be used in a try-catch block" >> {
      import java.util.Calendar

      val then = null
      val now = Calendar.getInstance()

      try {
        now.compareTo(then)
      } catch {
        case e: NullPointerException => println("One was null!")
        case unknown => println("Unknown exception " + unknown)
      } finally {
        println("It all worked")
      }
    }
  }
}
