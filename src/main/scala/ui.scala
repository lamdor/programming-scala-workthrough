package ui

import observer._

abstract class Widget

trait Clickable {
  def click()
}

class Button(label: String) extends Widget with Clickable {
  def click() = {
    // logic to do some clicking stuff...
  }
}

trait ObservableClicks extends Clickable with Subject {
  abstract override def click() {
    super.click
    notifyObservers
  }
}

class ObservableButton(label:String) extends Button(label) with Subject {
  override def click() = {
    super.click
    notifyObservers
  }
}

trait VetoableClicks extends Clickable {
  val maxAllowed = 1
  private var count = 0

  abstract override def click() {
    if (count < maxAllowed) {
      super.click()
      count += 1
    }
  }

  def reset() = {
    count = 0
  }
}
