import org.specs._

object ReadingXmlSpecification extends Specification {
  import scala.xml._

  "Scala XML" should {
    "be read in from a string" in {
      val xmlInAString = """
      <sammich>
        <bread>wheat</bread> <meat>salami</meat> <condiments>
        <condiment expired="true">mayo</condiment>
        <condiment expired="false">mustard</condiment> </condiments>
      </sammich>
      """
      val someXML = XML.loadString(xmlInAString)
      someXML must haveClass[scala.xml.Elem]
    }

    "be a literal typoe" in {
      val someXML =
        <sammich>
          <bread>wheat</bread> <meat>salami</meat> <condiments>
          <condiment expired="true">mayo</condiment>
          <condiment expired="false">mustard</condiment> </condiments>
        </sammich>

      someXML must haveClass[scala.xml.Elem]
    }
  }

  "Exploring XML" should {
    val someXML =
      <sammich>
        <bread>wheat</bread>
        <meat>salami</meat>
        <condiments>
          <condiment expired="true">mayo</condiment>
          <condiment expired="false">mustard</condiment>
        </condiments>
      </sammich>

    """use \ as a projection""" in {
      "return the first child element that matches" in {
        val breadXML = someXML \ "bread"
        breadXML.text must beEqualTo("wheat")
      }

      "return an empty NodeSeq if no immediate child matches" in {
        val condiments = someXML \ "condiment"
        condiments.isEmpty must beTrue
      }
    }

    """use \\ as a full breadth search""" in {
      val condiments = someXML \\ "condiment"
      condiments.size must beEqualTo(2)
    }

    """use \ and \\ as a traversal mechanism""" in {
      val firstExpired = (someXML \\ "condiment")(0) \ "@expired"
      firstExpired.text must beEqualTo("true")
    }

    "be looped" in {
      val expiredCondiments = for {
        c <- someXML \\ "condiment"
        if ((c \ "@expired").text == "true")
      } yield c.text

      expiredCondiments.size must beEqualTo(1)
      expiredCondiments must containAll(List("mayo"))
    }

    "be pattern matched" in {
      val condiments = someXML match {
        case <sammich>{ ingredients @ _* }</sammich> =>
          for (cond @ <condiments>{_*}</condiments> <- ingredients )
            yield cond.text
      }

      println("condiments = " + condiments)
      condiments.size must beEqualTo(1)
    }
  }
}
