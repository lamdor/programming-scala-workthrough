package ui

import observer._

abstract class Widget {
  class Properties {
    import scala.collection.immutable.HashMap

    private var values: Map[String, Any] = new HashMap

    def size = values.size
    def get(key: String) = values.get(key)
    def update(key: String, value: Any) = {
      // do some pre-processing
      values = values.update(key, value)
      // do some post-processing
    }
  }
  val properties = new Properties
  def draw(): Unit
  override def toString(): String = "(widget)"
}

trait Clickable {
  def click()
}

class Button(label: String) extends Widget with Clickable {
  def click() = {
    // logic to do some clicking stuff...
  }
  def draw() = {
    // do drawing stuff
  }
  override def toString() = "(button: label=" + label + ", " + super.toString +")"
}


trait ObservableClicks extends Clickable with Subject {
  abstract override def click() = {
    super.click
    notifyObservers
  }
}

class ButtonClickObserver {
  var count = 0
  def receiveUpdate(button:Any) = count += 1
}


class ObservableButton(label:String) extends Button(label) with Subject {
  override def click() = {
    super.click
    notifyObservers
  }
}

trait VetoableClicks extends Clickable {
  var maxAllowed = 1
  private var count = 0

  abstract override def click() = {
    if (count < maxAllowed) {
      super.click()
      count += 1
    }
  }

  def reset() = {
    count = 0
  }
}

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

  def draw() = {
    // do drawing stuff...
  }
}

class RadioButtonWithCallbacks(var on: Boolean, label: String, clickCallbacks: List[() => Unit]) extends ButtonWithCallbacks(label, clickCallbacks) {

  def this(on: Boolean, label: String, clickCallback: () => Unit) = this(on, label, List(clickCallback))

  def this(on: Boolean, label: String) = this(on, label, Nil)
}
