import org.specs._

object SelfTypesSpecification extends Specification {
  "Self types" should {
    "allow a class to alias this" in {
      class C1 { self =>
        def talk(message: String) = "C1.talk " + message
        class C2 {
          class C3 {
            def talk(message: String) = self.talk("C3.talk " + message)
          }
          val c3 = new C3
        }
        val c2 = new C2
      }
      val c1 = new C1
      c1.talk("Hello") mustEqual "C1.talk Hello"
      c1.c2.c3.talk("Hello") mustEqual "C1.talk C3.talk Hello"
    }

    "allow for component design" in {
      trait Persistence {
        def startPersisitence: Unit
      }

      trait MidTier {
        def startMidTier: Unit
      }

      trait UI {
        def startUI: Unit
      }

      trait Database extends Persistence {
        def startPersisitence { println("starting database") }
      }

      trait ComputeCluster extends MidTier {
        def startMidTier { println("starting compute cluster") }
      }

      trait WebUI extends UI {
        def startUI { println("starting web UI") }
      }

      trait App {
        self: Persistence with MidTier with UI =>

        def run = {
          startPersisitence
          startMidTier
          startUI
        }
      }

      object MyApp extends App with Database with ComputeCluster with WebUI

      MyApp run

      true
    }
  }
}
