package bounds.abbrevlist

sealed abstract class AbbrevList[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: AbbrevList[A]

  def ::[B >: A](x: B): AbbrevList[B] = new bounds.abbrevlist.::(x, this)

  final def foreach(f: A => Unit) = {
    var these = this
    while (!these.isEmpty) {
      f(these.head)
      these = these.tail
    }
  }
}

case object AbbrevNil extends AbbrevList[Nothing] {
  def isEmpty = true
  def head: Nothing = throw new NoSuchElementException("Head of empty AbbrevList")
  def tail: Nothing = throw new NoSuchElementException("Tail of empty AbbrevList")
}

final case class ::[B](private var hd: B, private var tl: AbbrevList[B]) extends AbbrevList[B] {
  def isEmpty = false
  def head = hd
  def tail = tl
}
