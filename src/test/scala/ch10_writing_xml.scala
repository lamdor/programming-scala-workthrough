import org.specs._

object WritingXmlSpecification extends Specification {
  "Writing XML" should {
    "inject values using { and }" in {
      val name = "Bob"
      val bobXML =
        <person>
          <name>{ name }</name>
        </person>

      (bobXML \ "name").text must beEqualTo("Bob")
    }
  }
}
