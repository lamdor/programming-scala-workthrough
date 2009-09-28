import org.specs._

object ActorsSpecification extends Specification {
  "Scala actors" should {
    "be created from the Actor class" in {
      import scala.actors.Actor

      class Redford extends Actor {
        def act {
          println("A lot of what acting is, is paying attention")
        }
      }
      val robert = new Redford
      robert.start
    }

    "be created from the actor factory method" in {
      import scala.actors.Actor._

      val paulNewman = actor {
        println("To be an actor, you have to be a child")
      }

      true
    }

    "be able to receive messages" in {
      import scala.actors.Actor._

      val fussyActor = actor {
        loop {
          receive {
            case s: String => println("I got a String: " + s)
            case i: Int => println("I got a Int: " + i)
            case _ => println("I have no idea what I just got")
          }
        }
      }

      fussyActor ! "Hi there"
      fussyActor ! 42
      fussyActor ! 21.2
    }

    "have a mailbox with a definite size" in {
      import scala.actors.Actor._

      val countActor = actor {
        loop {
          receive {
            case "how many?" => println("I've got " + mailboxSize.toString + " messages in my queue.")
          }
        }
      }

      countActor ! 1
      countActor ! 2
      countActor ! 3
      countActor ! "how many?"


      countActor ! "how many?"
      countActor ! 4
      countActor ! "how many?"
    }
  }
}
