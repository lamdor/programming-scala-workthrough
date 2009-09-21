import org.specs._
import ui._

object CompanionObjectsSpecification extends Specification {
  "Scala Companion Objects" should {
    type MyPair[+A, +B] = Tuple2[A, B]
    object MyPair {
      def apply[A, B](x: A, y: B) = Tuple2(x,y)
    }

    "treat the object as a function if it has the apply method" >> {
      val myPair = MyPair(1, "two")
      myPair._1 must_== 1
      myPair._2 must_== "two"
    }
  }

  "Widget companion object" should {
    "instantiate subclasses based on the text" >> {
      Widget("(button: label=Something, (widget))") match {
        case Some(w) => w match {
          case b: Button => b.label must_== "Something"
        }
        case None => fail("None returned")
      }

      Widget("(textfield: text=Something else, (widget))") match {
        case Some(w) => w match {
          case tf: TextField => tf.text must_== "Something else"
        }
        case None => fail("None returned")
      }

      Widget("(should not match)") match {
        case Some(w) => fail("Something was returned")
        case None => true
      }
    }
  }
}
