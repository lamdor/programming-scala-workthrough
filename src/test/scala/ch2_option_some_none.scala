import org.specs._
import org.specs.runner.JUnit4

class OptionSomeNoneSpecificationTest extends JUnit4(OptionSomeNoneSpecification)

object OptionSomeNoneSpecification extends Specification {
  val stateCapitals = Map(
    "Alabama" -> "Montgomery",
    "Alaska" -> "Juneau",
    // ...
    "Wyoming" -> "Cheyenne"
  )

  stateCapitals.get("Alabama") must_== Some("Montgomery")
  stateCapitals.get("Alaska") must_== Some("Juneau")
  stateCapitals.get("Wyoming") must_== Some("Cheyenne")

  stateCapitals.get("Alabama").get must_== "Montgomery"
  stateCapitals.get("Alaska").get must_== "Juneau"

  stateCapitals.get("Alabama") must beSome[String]
  stateCapitals.get("Alaska") must beSome[String].which(_ == "Juneau")

  stateCapitals.get("Iowa").getOrElse("Des Moines") must_== "Des Moines"
  stateCapitals.getOrElse("Iowa", "Des Moines") must_== "Des Moines"

  stateCapitals.get("Alabama").getOrElse("Unknown") must_== "Montgomery"
  stateCapitals.getOrElse("Alabama", "Unknown") must_== "Montgomery"
}
