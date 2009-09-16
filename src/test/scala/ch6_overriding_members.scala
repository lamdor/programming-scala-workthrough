import org.specs._
import ui._

object OverridingMembersSpecification extends Specification {

  class NotFixed {
    final def fixedMethod = "fixed"
  }

  class Changeable2 extends NotFixed {
    // override def fixedMethod = "notFixed" // error method fixedMethod cannot override final member

  }

  class Fixed {
    def doSomething = "Fixed did something"
  }

  class Changeable1 extends Fixed {
    override def doSomething = "No Changeable1 did something"
  }

  "Button" should {
    "show draw" >> {
      val button = new Button("hello")
      button.click
    }
    "have a sane toString" >> {
      val button = new Button("Ok")
      button.toString must_== "(button: label=Ok, (widget))"
    }
  }

  "Scala 2.7" should {
    "allow you to override superclass vals" >> {
      class S1 {
        val something = "abc"
      }
      class SubClass1 extends S1 {
        override val something = "mine"
      }
      val sc1 = new SubClass1
      sc1.something must_== "mine"
    }

    "not complain if you override defaulted trait vals, but not follow through" >> {
      trait T1 {
        val name = "T1"
      }
      class ClassExtendsT1 extends T1 {
        override val name = "ClassExtendsT1"
      }
      val cet1 = new ClassExtendsT1
      cet1.name must_== "T1"
    }

    "allow you to override abstract trait vals" >> {
      trait T1 {
        val name: String
      }
      class ClassExtendsT1 extends T1 {
        override val name = "ClassExtendsT1"
      }
      val cet1 = new ClassExtendsT1
      cet1.name must_== "ClassExtendsT1"
    }
  }

  "VetoableClicks" should {
    "allow the maxAllowed clicks to changed" >> {
      val button = new Button("Ok") with ObservableClicks with VetoableClicks {
        maxAllowed = 2
      }
      button.maxAllowed must_== 2

      val clickObserver = new ButtonClickObserver
      button.addObserver(clickObserver)

      for (i <- 1 to 4) button.click
      clickObserver.count must_== 2
    }
  }

}

