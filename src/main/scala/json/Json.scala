package json

import io.circe._
import io.circe.JsonNumber

case class User(username: String, age: Int, avatar: String)

object Json {
  val adam =
    """{
      |"username": "Adam",
      |"age": 36
      |}""".stripMargin
//    JObject(
//      ("username", JString("Adam")),
//      ("age", JNumber(JsonNumber.fromString("36").get))
//    )

  def decode(raw: String): Either[Error, User] = {
    for {
      json <- parser.parse(adam)
      cursor = json.hcursor
      username <- cursor.downField("username").as[String]
      avatar <- cursor.downField("avatar").as[String]
      age <- cursor.downField("age").as[Int]
    } yield User(username, age, avatar)
  }
}
