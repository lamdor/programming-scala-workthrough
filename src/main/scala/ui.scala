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
