import org.specs._
import ui._

object NestedClassesSpecification extends Specification {
  "Scala" should {
    "allow nested classes" >> {
      class MyWidget extends Widget

      val myWidget = new MyWidget

      myWidget.properties.update("abc", 123)

      myWidget.properties.size must_== 1
      myWidget.properties.get("abc").get must_== 123
    }
  }
}
