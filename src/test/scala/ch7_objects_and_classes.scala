import org.specs._

object ObjectsAndClassesSpecification extends Specification {
  "Sealed objects" should {
    "only allow subclasses declared in the same file" in {
      sealed abstract class HttpMethod() {
        def body: String
        def bodyLength =  body.length
      }
      case class Connect(body: String) extends HttpMethod
      case class Delete(body: String) extends HttpMethod
      case class Head(body: String) extends HttpMethod
      case class Get(body: String) extends HttpMethod
      case class Post(body: String) extends HttpMethod
      case class Put(body: String) extends HttpMethod
      case class Options(body: String) extends HttpMethod
      case class Trace(body: String) extends HttpMethod

      def handle(method: HttpMethod) = method match {
        case Connect(body) => "connect: " + body
        case Head(body) => "head: " + body
        case Get(body) => "get: " + body
        case Delete(body) => "delete: " + body
        case Post(body) => "post: " + body
        case Put(body) => "put: " + body
        case Options(body) => "options: " + body
        case Trace(body) => "trace: " + body
      }

      val methods = List(
        Connect("connect body..."),
        Head("head body..."),
        Get("get body..."),
        Delete("delete body..."),
        Post("post body..."),
        Put("put body..."),
        Options("options body..."),
        Trace("trace body...")
      )

      handle(methods(0)) must_== "connect: connect body..."
      handle(methods(2)) must_== "get: get body..."

      methods(0).bodyLength must_== 15
    }
  }
}
