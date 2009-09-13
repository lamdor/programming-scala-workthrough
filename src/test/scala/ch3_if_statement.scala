import org.specs._

object IfStatementSpecification extends Specification {
  "the if statement" should {

    "work just like a regular if statment" >> {
      def doSomething(i: Int) = {
        if (i < 5) {
          "Below"
        } else if (i == 5) {
          "Right on"
        } else {
          "Above"
        }
      }
      doSomething(3) must_== "Below"
      doSomething(5) must_== "Right on"
      doSomething(10) must_== "Above"
    }

    "allow shorter statements" >> {
      def doSomething(i: Int) = {
        if (i < 5) "Below"
        else if (i == 5) "Right on"
        else "Above"
      }
      doSomething(3) must_== "Below"
      doSomething(5) must_== "Right on"
      doSomething(10) must_== "Above"
    }

    "be able to return an assignment value" >> {
      def doSomething(i: Int) = {
        val result = if (i < 5) "Below"
                     else if (i == 5) "Right on"
                     else "Above"
        result
      }
      doSomething(3) must_== "Below"
      doSomething(5) must_== "Right on"
      doSomething(10) must_== "Above"
    }

  }
}
