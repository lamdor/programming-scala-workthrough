import org.specs._
import org.specs.runner.JUnit4

class ImportingTypesSpecificationTest extends JUnit4(ImportingTypesSpecification)

object ImportingTypesSpecification extends Specification {
  import java.awt._
  import java.io.File
  import java.io.File._
  import java.util.{ Map, HashMap }

  "imports" should {
    "be able to import static fields" in {
      import java.math.BigInteger.{
        ONE => _,
        TEN,
        ZERO => JAVAZERO
      }

      // ONE must_== 1 // error: not found: value ONE

      TEN.toString must_== "10"
      JAVAZERO.toString must_== "0"
    }

    "be relative to what already is imported" in {
      // all the same
      import scala.collection.mutable._
      // since scala is already imported
      // however there is org.specs.collection which this will try and import
      // import collection.mutable._
      import _root_.scala.collection.mutable._

      true
    }
  }

}
