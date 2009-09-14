package ui

import observer._

abstract class Widget

class Button(label: String) extends Widget {
  def click() = {
    // logic to do some clicking stuff...
  }
}

class ObservableButton(label:String) extends Button(label) with Subject {
  override def click() = {
    super.click
    notifyObservers
  }
}
