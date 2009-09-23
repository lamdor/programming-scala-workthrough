import org.specs._

object EqualitySpecification extends Specification {
  case class Point(x: Double, y: Double)

  val p1 = Point(2.1,3.2)
  val p2 = Point(2.1,3.2)
  val p3 = Point(1.0,-1.2)

  "equals method" should {
    "test for value equality" in {
      (p1 equals p2) must beTrue
      (p1 equals p3) must beFalse
      (p2 equals p3) must beFalse
      (p3 equals p1) must beFalse
      (p3 equals p2) must beFalse
      (p3 equals p3) must beTrue
    }
  }

  "== method" should {
    "delegate to the equals method" in {
      (p1 == p2) must beTrue
      (p1 == p3) must beFalse
      (p2 == p3) must beFalse
      (p3 == p1) must beFalse
      (p3 == p2) must beFalse
      (p3 == p3) must beTrue
    }
  }

  "!= method" should {
    "be equal to !(obj1 == obj2)" in {
      (p1 != p2) must beFalse
      (p1 != p3) must beTrue
      (p2 != p3) must beTrue
      (p3 != p1) must beTrue
      (p3 != p2) must beTrue
      (p3 != p3) must beFalse
    }
  }

  "eq method" should {
    "test for reference equality" in {
      (p1 eq p1) must beTrue
      (p2 eq p1) must beFalse
      (p2 eq p2) must beTrue
    }
  }

  "ne method" should {
    "be the negation of eq" in {
      (p1 ne p1) must beFalse
      (p2 ne p1) must beTrue
    }
  }

  "Array equality" should {
    val a1 = Array(1,2)
    val a2 = Array(1,2)

    "not be obvious using the == method" in {
      (a1 == a2) must beFalse
      (a1 == a1) must beTrue
    }

    "test equality using the sameElements method" in {
      (a1 sameElements a2) must beTrue
      (a1 sameElements a1) must beTrue
    }
  }

  "List equality" should {
    val l1 = List(1,3)
    val l2 = List(1,3)
    val l3 = List(-1,4)

    "be able to use the == method to test value equality" in {
      (l1 == l2) must beTrue
      (l1 == l3) must beFalse
    }
  }
}
