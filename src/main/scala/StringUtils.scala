object StringUtils {
  def joiner(strings: List[String], seperator: String) = strings.mkString(seperator)
  def joiner(strings: List[String]): String = joiner(strings, " ")
}
