import org.specs._

import shapes._

object shapeSpecification extends Specification {
  "Point#toString includes description" in {
    val point = Point(2.0, 3.4)
    point.toString must beEqualTo("Point(2.0,3.4)")
  }

  "Circle#toString includes description" in {
    val circle = Circle(Point(2.0, 3.4), 2.0)
    circle.toString must beEqualTo("Circle(Point(2.0,3.4),2.0)")
  }

  "Rectangle#toString includes description" in {
    val rectangle = Rectangle(Point(2.0, 3.4), 2.0, 3.0)
    rectangle.toString must beEqualTo("Rectangle(Point(2.0,3.4),2.0,3.0)")
  }

  "Triangle#toString includes description" in {
    val triangle = Triangle(Point(2.0, 3.4), Point(4.0, 5.0), Point(6.0, 7.0))
    triangle.toString must beEqualTo("Triangle(Point(2.0,3.4),Point(4.0,5.0),Point(6.0,7.0))")
  }

  "Shapes case classes" should {
    "extract fields via a unapply method on the companion object" in {
      val circle = Circle(Point(2.0, 3.2), 4.0)

      circle match {
        case Circle(point, radius) => {
          point must beEqualTo(Point(2.0, 3.2))
          radius must beEqualTo(4.0)
          point match {
            case Point(x,y) => {
              x must beEqualTo(2.0)
              y must beEqualTo(3.2)
            }
          }
        }
      }
    }
  }

}
