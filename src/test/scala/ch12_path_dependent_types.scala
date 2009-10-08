import org.specs._

object PathDependentTypesSpecification extends Specification {
  "Path depedent types" should {
    "force fully qualified types in nested classes" in {
      trait Service {
        trait Logger { def log(message: String): Unit }
        val logger: Logger
        def run = {
          logger.log("Starting " + getClass.getSimpleName + ":")
          doRun
        }
        protected def doRun: Boolean
      }

      object MyService1 extends Service {
        class MyService1Logger extends Logger {
          def log(message: String) { println("1: " + message) }
        }
        val logger = new MyService1Logger
        def doRun = true
      }

      // object MyService2 extends Service {
      //   val logger = MyService1.logger
      //   // error overriding value logger in trait Service of type PathDependentTypesSpecification.MyService2.Logger;
      //   // value logger has incompatible type MyService1.MyService1Logger
      //   def doRun = true
      // }

      MyService1 run
    }

    class C1 {
      var x = "1"
      def setX1(x: String) = this.x = x
      def setX2(x: String) = C1.this.x = x
    }

    "be able to use this.x and C1.this.x" in {
      val c = new C1
      c.setX1("A")
      c.x mustEqual "A"
      c.setX2("B")
      c.x mustEqual "B"
    }

    "be able to use C.super" in {
      class C2 extends C1
      class C3 extends C2 {
        def setX3(x: String) = super.setX1(x)
        def setX4(x: String) = C3.super.setX1(x)
        def setX5(x: String) = C3.super[C2].setX1(x)
      }

      val c3 = new C3
      c3.setX3("A")
      c3.x mustEqual "A"

      c3.setX4("B")
      c3.x mustEqual "B"

      c3.setX5("C")
      c3.x mustEqual "C"
    }

    "be able to refer to parent nested types" in {
      class C4 {
        class C5
      }
      class C6 extends C4 {
        val c5a = new C5
        val c5b = new super.C5
      }
      true
    }

    "be able to refer a nested type via period-delimited path expression" in {
      object P1 {
        object O1 {
          object O2 {
            val name = "name"
          }
        }
      }

      class C7 {
        val name = P1.O1.O2.name
      }

      (new C7).name mustEqual "name"
    }

    "be able to refer to types" in {
      object O3 {
        object O4 {
          type t = java.io.File
          class C
          trait T
        }

        class C2 {
          type t = Int
        }
      }

      class C8 {
        type t1 = O3.O4.t
        type t2 = O3.O4.C
        type t3 = O3.O4.T
      }

      true
    }
  }
}
