package shapes {
  case class Point(x: Double, y: Double)

  abstract class Shape() {
    def draw() : Unit
  }

  case class Circle(center: Point, radius: Double) extends Shape {
    def draw = println("Circle.draw: " + this)
  }

  case class Rectangle(lowerLeft: Point, height: Double, width: Double) extends Shape {
    def draw = println("Rectangle.draw: " + this)
  }

  case class Triangle(point1: Point, point2: Point, point3: Point) extends Shape {
    def draw = println("Triangle.draw: " + this)
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

      ShapeDrawingActor ! Circle(Point(2.0, 3.0), 2.0)
      ShapeDrawingActor ! Rectangle(Point(1.0, 2.0), 3.0, 4.0)
      ShapeDrawingActor ! Triangle(Point(1.0, 2.0), Point(3.0, 4.0), Point(5.0, 6.0))

      ShapeDrawingActor ! "something"

      ShapeDrawingActor ! "exit"
    }
  }

}
