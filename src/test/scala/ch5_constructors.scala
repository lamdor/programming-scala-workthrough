import org.specs._
import ui._

object ConstructorsSpecification extends Specification {
  "Scala classes" should {
    "allow overloaded constructors" >> {
      val nullList: List[() => Unit] = null
      (new ButtonWithCallbacks("hello", nullList)) must throwA(new IllegalArgumentException("requirement failed: Callback list cannot be null"))

      val button = new ButtonWithCallbacks("hello")
      button.click

      var clicked = false
      val buttonWithCallback = new ButtonWithCallbacks("hello",
                                                       () => clicked = true)
      buttonWithCallback.click
      clicked must beTrue

      var count = 0
      val buttonWithMultipleCallbacks = new ButtonWithCallbacks("hello",
                                                                List(
                                                                  () => count += 1,
                                                                  () => count += 2
                                                                ))
      buttonWithMultipleCallbacks.click
      count must_== 3
    }

    "should be able to call the super classes constructor" >> {
      var clicked = false
      val radioButton = new RadioButtonWithCallbacks(false, "Hello", () => clicked = true)
      radioButton.click
      clicked must beTrue
    }
  }
}
