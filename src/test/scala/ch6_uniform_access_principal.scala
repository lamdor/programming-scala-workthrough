import org.specs._

object UniformAcessPrincipalSpecification extends Specification {
  "Scala" should {
    "not allow fields and methods to have the same name" >> {
      class IllegalMemberNameUse {
        def member(i: Int) = 2 * i
        val member = 2
        // object member { // error member is already defined as value member
        //   def apply() = 2
        // }

      }
      true
    }

    "show allow a subclass to override a method with a val" >> {
      class Parent {
        def name = "Parent"
      }
      class Child extends Parent {
        override val name = "Child"
      }

      val child = new Child
      child.name must_== "Child"

      abstract class AbstractParent {
        def name: String
      }
      class ConcreteChild extends AbstractParent {
        val name = "Child"
      }

      val concreteChild = new ConcreteChild
      concreteChild.name must_== "Child"
    }
  }
}
