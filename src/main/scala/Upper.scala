object Upper {
  def upper(strings: String*) = strings.map(_.toUpperCase)

  def main(args: Array[String]) = {
    args.map(_.toUpperCase).foreach(printf("%s ", _))
    println("")
  }
}
