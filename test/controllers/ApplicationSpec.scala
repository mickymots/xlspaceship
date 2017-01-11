package controllers

import org.junit.runner._
import org.specs2.matcher._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test.Helpers._
import play.api.test._

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification with JsonMatchers {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "render the index page" in new WithApplication{
      val api = route(FakeRequest(GET, "/")).get

      status(api)          must equalTo(OK)
      contentType(api)     must beSome.which(_ == "application/json")
      contentAsString(api) must /("root" -> "/")
    }
  }
}
