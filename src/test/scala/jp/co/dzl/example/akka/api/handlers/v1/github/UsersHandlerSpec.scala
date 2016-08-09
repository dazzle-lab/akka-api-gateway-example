package jp.co.dzl.example.akka.api.handlers.v1.github

import akka.http.scaladsl.model.{ HttpResponse, HttpRequest }
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import jp.co.dzl.example.akka.api.AkkaSupport
import org.scalatest.{ Matchers, FlatSpec }

import scala.concurrent.Future

class UsersHandlerSpec extends FlatSpec with Matchers with ScalatestRouteTest with AkkaSupport {
  trait Subject extends UsersHandler with HandlerTest {
    override val host = "api.github.com"
    override val port = 443
    override val timeout = 5
  }

  "GET /v1/github/users/:login" should "reverse proxy to github api" in new Subject {
    override def github(request: HttpRequest) = Future.successful(HttpResponse(OK))

    Get("/v1/github/users/username") ~> routes ~> check {
      status shouldBe OK
    }
  }

  "GET /v1/github/users/:login" should "respond service unavialable code if unreachable to github" in new Subject {
    override def github(request: HttpRequest) = Future.failed(new Exception)

    Get("/v1/github/users/username") ~> routes ~> check {
      status shouldBe ServiceUnavailable
    }
  }
}
