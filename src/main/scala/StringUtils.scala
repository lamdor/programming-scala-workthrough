object StringUtils {
  def joiner(strings: List[String], seperator: String) = strings.mkString(seperator)
  def joiner(strings: List[String]): String = joiner(strings, " ")

  def toCollection(s: String): Seq[String] = s.split(" ").toList
}
