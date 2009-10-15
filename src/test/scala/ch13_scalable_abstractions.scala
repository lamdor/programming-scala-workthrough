import org.specs._
import encodedstring._

object ScalableAbstractionSpecification extends Specification {
  "EncodedString" should {
    "support CSV" in {
      val es = EncodedString("first,second,third", EncodedString.Separator.COMMA)
      es.toString mustEqual "first,second,third"
      es.tokens mustEqual(List("first", "second", "third"))
    }

    "support TSV" in {
      val es = EncodedString("first\tsecond\tthird", EncodedString.Separator.TAB)
      es.toString mustEqual "first\tsecond\tthird"
      es.tokens mustEqual(List("first", "second", "third"))
    }
  }

  "ButtonSubjectObserver" should {
    "notify observers on click" in {
      import ui.ButtonSubjectObserver._

      val testObserver = new ButtonObserver {
        var notified: Boolean = false

        def receiveUpdate(button: ObservableButton): Unit = notified = true
      }

      val button = new ObservableButton("Ok")
      button.addObserver(testObserver)
      button.click()

      testObserver.notified must beTrue
    }
  }

  "ButtonClicksObserver" should {
    import ui.ButtonClicksObserver
    import ui.ButtonSubjectObserver._

    "count clicks for a named button" in {
      val buttons = List(
        new ObservableButton("Button 1"),
        new ObservableButton("Button 2"),
        new ObservableButton("Button 1")
      )

      val clicksObserver = new ButtonClicksObserver

      buttons.foreach { b => b.addObserver(observer) }

      buttons.foreach(_.click)

      clicksObserver.clicks.getOrElse("Button 1", 0) mustEqual 2
    }
  }
}
