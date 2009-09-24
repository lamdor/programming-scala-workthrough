import org.specs._

object LinearizationHierarchy extends Specification {
  "Scala method lookup" should {
    class Class1 {
      def m = List("Class1")
    }

    trait Trait1 extends Class1 {
      override def m = { "Trait1" :: super.m }
    }

    trait Trait2 extends Class1 {
      override def m = { "Trait2" :: super.m }
    }

    trait Trait3 extends Class1 {
      override def m = { "Trait3" :: super.m }
    }

    class Class2 extends Trait1 with Trait2 with Trait3 {
      override def m = { "Class2" :: super.m }
    }

    "go right to left in the hierarchy" in {
      val c2 = new Class2
      c2.m must_== List("Class2", "Trait3", "Trait2", "Trait1", "Class1")
    }
  }

  "Scala constructor construction" should {
    "go left to right, top to bottom" in {
      var clist: List[String] = Nil
      class Class1 {
        clist ::= "Class1"
      }

      trait Trait1 extends Class1 {
        clist ::= "Trait1"
      }

      trait Trait2 extends Class1 {
        clist ::= "Trait2"
      }

      trait Trait3 extends Class1 {
        clist ::= "Trait3"
      }

      class Class2 extends Trait1 with Trait2 with Trait3 {
        clist ::= "Class2"
      }

      clist must beEmpty
      val c2 = new Class2
      clist = clist.reverse // since :: prepends to front
      clist must_== List("Class1", "Trait1", "Trait2", "Trait3", "Class2")
    }
  }
}
