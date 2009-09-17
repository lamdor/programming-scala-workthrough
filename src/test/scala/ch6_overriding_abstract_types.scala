import org.specs._
import observer._

object OverrideAbstractTypesSpecification extends Specification {
  "Scala" should {
    "allow subclasses to override abstract types - example 1" >>  {
      class FakeObserver {
        var called = false
        def receiveUpdate(subject: Any) { called = true }
      }
      val fakeObserver = new FakeObserver

      class FakeSubjectWithObserver extends SubjectForReceiveUpdateObservers
      val fakeSubjectWithObserver = new FakeSubjectWithObserver

      fakeSubjectWithObserver.addObserver(fakeObserver)

      fakeSubjectWithObserver.notifyObservers

      fakeObserver.called must beTrue
    }

    "allow subclasses to override abstract types - example 2" >>  {
      var called = false
      val toCall = (subj: AbstractSubject) => { called = true }

      class FakeSubjectWithFunctionalObserver extends SubjectForFunctionalObservers
      val fakeSubject = new FakeSubjectWithFunctionalObserver
      fakeSubject.addObserver(toCall)

      fakeSubject.notifyObservers

      called must beTrue
    }
  }
}
