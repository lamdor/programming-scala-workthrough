import org.specs._

object TypeBoundsSpecification extends Specification {
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  "Scala type bounds" should {
    "allow subtypes" in {
      class SomethingDoer[A] {
        def doSomething[B <: A](b: B) = true
      }

      var doer = new SomethingDoer[Cat]
      doer.doSomething(new Cat) must beTrue
      // doer.doSomething(new Animal) must beTrue // inferred type arguments [TypeBoundsSpecification.Animal] do not conform to method doSomething's type parameter bounds [B <: TypeBoundsSpecification.Cat]

      val doer2 = new SomethingDoer[Animal]
      doer2.doSomething(new Cat) must beTrue
      doer2.doSomething(new Animal) must beTrue
      doer2.doSomething(new Dog) must beTrue
    }

    "allow supertypes" in {
      class SomethingDoer[A] {
        def doSomething[B >: A](b: B) = true
      }

      var doer = new SomethingDoer[Cat]
      doer.doSomething(new Cat) must beTrue
      doer.doSomething(new Animal) must beTrue
      doer.doSomething(new Dog) must beTrue // has a common supertype of Animal
    }
  }
}

import bounds.abbrevlist._

object AbbrevListSpecification extends Specification {
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  "AbbrevList" should {
    "be able to be empty" in {
      var myList = AbbrevNil
      myList.isEmpty must beTrue
    }

    "allow items to prepend to it" in {
      var myList: AbbrevList[AnyRef] = AbbrevNil
      myList = new Cat :: myList
      myList = new Animal :: myList
      myList = new Dog :: myList

      myList.isEmpty must beFalse
    }

    "be able to loop" in {
      val lou = new Cat
      val bear = new Animal
      var myList = bear :: lou :: AbbrevNil

      var results: List[Animal] = Nil
      myList.foreach { a => results = a :: results }
      val expectedResults = List(lou, bear)
      results mustEqual expectedResults
    }
  }
}

import bounds._

object NodeSpecification extends Specification {
  "Node" should {
    "compose using the case clase bounds.::" in {
      val node = bounds.::(1, bounds.::(2, NilNode))
      node.toString mustEqual "(1 :: (2 :: NilNode))"
    }
  }

  "LinkedList" should {
    implicit def any2Node[A](x: A): Node[A] = bounds.::[A](x, NilNode)
    "use a view bound to convert AnyRef's to a Node" in {
      val llist = 1 :: "b" :: 3 :: LinkedList(4)
      llist.toString mustEqual "(1 :: (b :: (3 :: (4 :: NilNode))))"
    }
  }
}
