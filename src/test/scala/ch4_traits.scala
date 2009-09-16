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
      val buttonClickObserver = new ButtonClickObserver
      button.addObserver(buttonClickObserver)

      button.click
      buttonClickObserver.count must_== 1

      button.click
      buttonClickObserver.count must_== 2
    }
  }

  "Scala traits" should {
    "be able to be applied to just an object" in {
      val button = new Button("Okay") with Subject {
        override def click() = {
          super.click
          notifyObservers
        }
      }
      val buttonClickObserver = new ButtonClickObserver
      button.addObserver(buttonClickObserver)

      for (i <- 1 to 3) button.click
      buttonClickObserver.count must_== 3
    }

    "be able to be applied anonymously" in {
      val button = new Button("Okay") with ObservableClicks
      val buttonClickObserver = new ButtonClickObserver
      button.addObserver(buttonClickObserver)

      for (i <- 1 to 3) button.click
      buttonClickObserver.count must_== 3
    }

    "be constructed in a left to right manner" in {
      trait T1 {
        println("  in T1: x = " + x)
        val x = 1
        println("  in T1: x = " + x)
      }
      trait T2 {
        println("  in T2: y = " + y)
        val y = "T2"
        println("  in T2: y = " + y)
      }
      class Base12 {
        println("  in Base12: b = " + b)
        val b = "Base12"
        println("  in Base12: b = " + b)
      }
      class C12 extends Base12 with T1 with T2 {
        println("  in C12: c = " + c)
        val c = "C12"
        println("  in C12: c = " + c)
      }
      println("Creating C12:")
      new C12
      println("After Creating C12")
    }
  }

  "VetoableClicks" should {
    "allow a maximum number of clicks" in {
      val button = new Button("Okay") with ObservableClicks with VetoableClicks
      val buttonClickObserver = new ButtonClickObserver
      button.addObserver(buttonClickObserver)

      for (i <- 1 to 3) button.click
      buttonClickObserver.count must_== 1
    }

    "be able to be reset" in {
      val button = new Button("Okay") with ObservableClicks with VetoableClicks
      val buttonClickObserver = new ButtonClickObserver
      button.addObserver(buttonClickObserver)

      for (i <- 1 to 3) button.click
      buttonClickObserver.count must_== 1

      button.reset
      for (i <- 1 to 3) button.click
      buttonClickObserver.count must_== 2
    }
  }

}
