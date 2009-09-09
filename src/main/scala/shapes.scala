package shapes {
  class Point(val x: Double, val y: Double) {
    override def toString() = "Point(" + x + ", " + y +")"
  }

  abstract class Shape() {
    def draw() : Unit
  }

  class Circle(val center: Point, val radius: Double) extends Shape {
    def draw = println("Circle.draw: " + this)
    override def toString() = "Circle(" + center + ", " + radius + ")"
  }

  class Rectangle(val lowerLeft: Point, val height: Double, val width: Double) extends Shape {
    def draw = println("Rectangle.draw: " + this)
    override def toString() = "Rectangle(" + lowerLeft + ", " + height + ", " + width + ")"
  }

  class Triangle(val point1: Point, val point2: Point, val point3: Point) extends Shape {
    def draw = println("Triangle.draw: " + this)
    override def toString() = "Triangle(" + point1 + ", " + point2 + ", " + point3 + ")"
  }

  import scala.actors._
  import scala.actors.Actor._

  object ShapeDrawingActor extends Actor {
    def act() {
      loop {
        receive {
          case s: Shape => s.draw()
          case "exit" => println("Exiting..."); exit
          case x: Any => println("Error! Unknown message: " + x)
        }
      }
    }
  }

  object ShapeMain {
    def main(args: Array[String]) = {
      ShapeDrawingActor.start()

      ShapeDrawingActor ! new Circle(new Point(2.0, 3.0), 2.0)
      ShapeDrawingActor ! new Rectangle(new Point(1.0, 2.0), 3.0, 4.0)
      ShapeDrawingActor ! new Triangle(new Point(1.0, 2.0), new Point(3.0, 4.0), new Point(5.0, 6.0))

      ShapeDrawingActor ! "something"

      ShapeDrawingActor ! "exit"
    }
  }

}
