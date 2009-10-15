package encodedstring {

  trait EncodedString {
    protected[encodedstring] val string: String
    val separator: EncodedString.Separator.Delimiter

    override def toString = string
    def tokens = string.split(separator.toString).toList
  }

  object EncodedString {
    object Separator extends Enumeration {
      type Delimiter = Value
      val COMMA = Value(",")
      val TAB = Value("\t")
    }
    def apply(s: String, sep: Separator.Delimiter): EncodedString = sep match {
      case Separator.COMMA => impl.CSV(s)
      case Separator.TAB   => impl.TSV(s)
    }
    def unapply(es: EncodedString) = Some(Pair(es.string, es.separator))
  }

  package impl {
    private[encodedstring] case class CSV(val string: String) extends EncodedString {
      val separator = EncodedString.Separator.COMMA
    }
    private[encodedstring] case class TSV(val string: String) extends EncodedString {
      val separator = EncodedString.Separator.TAB
    }
  }
}
