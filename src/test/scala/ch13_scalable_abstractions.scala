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
}
