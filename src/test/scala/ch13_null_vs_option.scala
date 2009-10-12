import org.specs._

object NullVsOptionsSpecification extends Specification {
  "Scala Options" should {
    case class User(userName: String, name: String, email: String, bio: String)

    val newUserProfiles = List(
      Map("userName" -> "twitspam", "name" -> "Twit Spam"),
      Map("userName" -> "rubbish", "name" -> "Luke Amdor",
          "email" -> "luke.amdor@gmail.com", "bio" -> "big nerd"),
      Map("userName" -> "bucktrends", "name" -> "Buck Trends",
          "email" -> "buck@trends.com", "bio" -> "Bucks the Trends")
    )

    "prevent you from using null" in {
      val validUsersOldWay = for {
        user     <- newUserProfiles
        if (user.contains("userName") && user.contains("name") &&
            user.contains("email") && user.contains("bio"))
        userName <- user get "userName"
        name     <- user get "name"
        email    <- user get "email"
        bio      <- user get "bio"
      } yield User(userName, name, email, bio)

      val validUsersNewWay = for {
        user     <- newUserProfiles
        userName <- user get "userName"
        name     <- user get "name"
        email    <- user get "email"
        bio      <- user get "bio"
      } yield User(userName, name, email, bio)

      validUsersNewWay mustEqual(validUsersOldWay)

      validUsersNewWay.size mustEqual(2)
      validUsersNewWay mustContain(new User("rubbish", "Luke Amdor",
                                            "luke.amdor@gmail.com", "big nerd"))
      validUsersNewWay mustContain(new User("bucktrends", "Buck Trends",
                                            "buck@trends.com", "Bucks the Trends"))

      val validUsersUnwound = newUserProfiles flatMap {
        case user => user.get("userName") flatMap {
          case userName => user.get("name") flatMap {
            case name => user.get("email") flatMap {
              case email => user.get("bio") map {
                case bio => User(userName, name, email, bio)
              }
            }
          }
        }
      }

      validUsersNewWay mustEqual(validUsersUnwound)
    }

    "when concat'd with a list add nothing" in {
      val result = List(1,2,3) ++ None ++ List(4,5,6) ++ None
      result mustEqual(List(1,2,3,4,5,6))
    }
  }
}
