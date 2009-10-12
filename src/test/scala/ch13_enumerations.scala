import org.specs._

object Ch13EnumerationsSpecification extends Specification {
  "Scala Enumerations" should {
    "be defined by classes (like annotations)" in {
      object HttpMethod extends Enumeration {
        type Method = Value
        val Connect, Delete, Get, Head, Options, Post, Put, Trace = Value
      }

      import HttpMethod._

      def handle(method: HttpMethod.Method) = method match {
        case Connect => "using Connect"
        case Delete  => "using Connect"
        case Get     => "using Get"
        case m: HttpMethod.Method => String.format("using %s (I think)", m.toString)
      }

      Connect.id mustEqual 0
      Delete.id mustEqual 1
      Trace.id mustEqual 7

      handle(Connect) mustEqual "using Connect"
      handle(Get) mustEqual "using Get"

      HttpMethod foreach { method => println(handle(method)) }

      true
    }

    "can just be faked using case classes" in {
      sealed abstract class HttpMethod(val id: Int) {
        def name = getClass getSimpleName
        override def toString = name
      }

      case object Connect extends HttpMethod(0)
      case object Delete extends HttpMethod(1)
      case object Get extends HttpMethod(2)
      case object Head extends HttpMethod(3)
      case object Options extends HttpMethod(4)
      case object Post extends HttpMethod(5)
      case object Put extends HttpMethod(6)
      case object Trace extends HttpMethod(7)

      object HttpMethod {
        private val methods: List[HttpMethod] = List(Connect, Delete, Get, Head, Options, Post, Put, Trace)
        def foreach(f: HttpMethod => Unit) = methods.foreach(f)
      }

      def handle(method: HttpMethod) = method match {
        case Connect => "using Connect"
        case Delete  => "using Connect"
        case Get     => "using Get"
        case m: HttpMethod => String.format("using %s (I think)", m.toString)
      }

      Connect.id mustEqual 0
      Delete.id mustEqual 1
      Trace.id mustEqual 7

      handle(Connect) mustEqual "using Connect"
      handle(Get) mustEqual "using Get"

      HttpMethod foreach { method => println(handle(method)) }
    }
  }
}
