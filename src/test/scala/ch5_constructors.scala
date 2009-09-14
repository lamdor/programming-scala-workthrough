import org.specs._
import ui._

object ConstructorsSpecification extends Specification {
  "Scala classes" should {
    "allow overloaded constructors" >> {
      class ButtonWithCallbacks(val label: String, val clickedCallbacks: List[() => Unit]) extends Widget {

        require(clickedCallbacks != null, "Callback list cannot be null")

        def this(label: String, clickedCallback: () => Unit) =
          this(label, List(clickedCallback))

        def this(label: String) = {
          this(label, Nil)
          println("Warning.. no callback")
        }

        def click() = {
          // pretend to click
          clickedCallbacks.foreach(f => f())
        }
      }

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
  }
}
