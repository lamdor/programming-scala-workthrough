class Upper {
  def upper(strings:String*) = {
    strings.map((s:String) => s.toUpperCase)
  }
}
