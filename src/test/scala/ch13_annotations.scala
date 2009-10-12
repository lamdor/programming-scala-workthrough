import org.specs._

object AnnotationsSpecification extends Specification {
  "Scala annotations" should {
    import org.contract4j5.contract._
    class Person(
      @Pre("name != null && name.length() > 0")
      val name: String,
      @Pre{val value = "age > 0", val message = "You're too young!"}
      val age: Int
    )

    class SSN(
      @Pre("valid(ssn)") { val message = "Format must be DDD-DD-DDDD"}
      val ssn: String) {
      private def valid(value: String) {
        value matches """^\s*\d{3}-\d{2}-\d{4}\s*$"""
      }
    }

    "be just like java annotations" in {
      val person = new Person("", -1)
      person.name mustEqual ""
      person.age mustEqual -1

      val ssn = new SSN("1234")
      ssn.ssn mustEqual "1234"
    }

    "be created just like a normal class extends StaticAnnotation" in {
      class MyAnnotation(val value: String) extends StaticAnnotation

      class MyClass(
        @MyAnnotation("Some value")
        val value: String)

      true
    }

    "doesn't allow implicit conversions as part of the contructor argument" in {
      class Persist(tableName: String, params: Map[String, Any]) extends StaticAnnotation

      // value -> is not a member of java.lang.String
      // @Persist("Accounts", Map("null" -> false, "primaryKey" -> "AccountId"))
      @Persist("Accounts", Map(("null", false), ("primaryKey", "AccountId")))
      class Account

      true
    }
  }

  "the throws annotation" should {
    "clue the client to what exceptions a method could throw" in {
      import java.io._

      class FilePrinter(val file: File) {

        @throws(classOf[IOException])
        def print() = {
          var reader: LineNumberReader = null
          try {
            reader = new LineNumberReader(new FileReader(file))
            loop(reader)
          } finally {
            if (reader != null)
              reader.close
          }
        }

        private def loop(reader: LineNumberReader) {
          val line = reader.readLine
          if (line != null) {
            format("%3d: %s\n", reader.getLineNumber, line)
            loop(reader)
          }
        }
      }

      val printer = new FilePrinter(new File("src/test/scala/ch13_annotations.scala"))
      printer.print()
    }
  }
}
