import org.specs._

import ui._
import observer._

object TraitsSpecification extends Specification {
  "Button" should {
    "click" >> {
      val button = new Button("Hello")
      button click
    }
  }

  class TestObserver {
    var called = false
    def receiveUpdate(subject: Any) = called = true
  }

  "Subject" should {
    "notify observers" in {
      class TestSubject extends Subject {
        def doAction() = notifyObservers
      }

      val subject = new TestSubject
      val testObserver = new TestObserver
      subject.addObserver(testObserver)

      subject doAction

      testObserver.called must beTrue
    }
  }

  "ObservableButton" should {
    "notify observers on click" in {
      val button = new ObservableButton("hello")

      val testObserver = new TestObserver
      button.addObserver(testObserver)

      button.click
      testObserver.called must beTrue
    }

    "keep notifying observers on every click" in {
      val button = new ObservableButton("hello")
      class ButtonClickObserver {
        var count = 0
        def receiveUpdate(button:Any) = count += 1
      }
      val buttonClickObserver = new ButtonClickObserver
      button.addObserver(buttonClickObserver)

      button.click
      buttonClickObserver.count must_== 1

      button.click
      buttonClickObserver.count must_== 2
    }
  }

}
