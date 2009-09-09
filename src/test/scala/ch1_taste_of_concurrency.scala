import org.specs._
import org.specs.runner.JUnit4

import shapes._

class ShapeSpecificationTest extends JUnit4(shapeSpecification)

object shapeSpecification extends Specification {
  "Point#toString includes description" in {
    val point = new Point(2.0, 3.4)
    point.toString must beEqualTo("Point(2.0, 3.4)")
  }

  "Circle#toString includes description" in {
    val circle = new Circle(new Point(2.0, 3.4), 2.0)
    circle.toString must beEqualTo("Circle(Point(2.0, 3.4), 2.0)")
  }

  "Rectangle#toString includes description" in {
    val rectangle = new Rectangle(new Point(2.0, 3.4), 2.0, 3.0)
    rectangle.toString must beEqualTo("Rectangle(Point(2.0, 3.4), 2.0, 3.0)")
  }

  "Triangle#toString includes description" in {
    val triangle = new Triangle(new Point(2.0, 3.4), new Point(4.0, 5.0), new Point(6.0, 7.0))
    triangle.toString must beEqualTo("Triangle(Point(2.0, 3.4), Point(4.0, 5.0), Point(6.0, 7.0))")
  }

}
