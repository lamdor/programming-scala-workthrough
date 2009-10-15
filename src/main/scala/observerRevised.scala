package observerRevised

abstract class SubjectObserver {
  type O <: Observer
  type S <: Subject

  trait Subject {
    self: S =>
    private var observers = List[O]()
    def addObserver(observer: O) = observers ::= observer
    def notifyObservers() = observers.foreach(_.receiveUpdate(self))
  }

  trait Observer {
    def receiveUpdate(subject: S)
  }
}

