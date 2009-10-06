package bounds

abstract trait Node[+A] {
  def payload: A
  def next: Node[A]
}

case class ::[+A](val payload: A, val next: Node[A]) extends Node[A] {
  override def toString =
    String.format("(%s :: %s)", payload.toString, next.toString)
}

object NilNode extends Node[Nothing] {
  def payload = throw new NoSuchElementException("No payload in NilNode")
  def next = throw new NoSuchElementException("No next in NilNode")
  override def toString = "NilNode"
}

case class LinkedList[A <% Node[A]](val head: Node[A]) {
  def ::[B >: A <% Node[B]](x: Node[B]) =
    LinkedList(bounds.::(x.payload, head))

  override def toString = head.toString
}
