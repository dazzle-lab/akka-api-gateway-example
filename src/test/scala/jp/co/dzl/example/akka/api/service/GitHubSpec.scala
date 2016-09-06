package jp.co.dzl.example.akka.api.service

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ HttpMethods, HttpRequest, HttpResponse }
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ Flow, Source }
import akka.stream.testkit.scaladsl.TestSink
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ BeforeAndAfterAll, FlatSpec, Matchers }

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class GitHubSpec extends FlatSpec with Matchers with ScalaFutures with BeforeAndAfterAll with MockFactory {
  implicit val system = ActorSystem("github-spec")
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  override protected def afterAll: Unit = {
    Await.result(system.terminate(), Duration.Inf)
  }

  "#from" should "merge original headers to github request" in {
    val github = new GitHubImpl("127.0.0.1", 8000, 5, mock[HttpClient])
    val request = HttpRequest(HttpMethods.GET, "/")
      .addHeader(RawHeader("host", "dummy"))
      .addHeader(RawHeader("timeout-access", "dummy"))

    val result = Source.single(HttpRequest(HttpMethods.GET, "/v1/github/users/xxxxxx"))
      .via(github.from(request))
      .runWith(TestSink.probe[HttpRequest])
      .request(1)
      .expectNext()

    result.headers.filter(_.lowercaseName() == "host") shouldBe empty
    result.headers.filter(_.lowercaseName() == "timeout-access") shouldBe empty
    result.headers.filter(_.lowercaseName() == "x-forwarded-host") shouldNot be(empty)
  }

  "#send" should "connect using http client" in {
    val httpResponse = HttpResponse()
    val httpClient = mock[HttpClient]
    (httpClient.connectionHttps _).expects(*, *, *).returning(Flow[HttpRequest].map(_ => httpResponse))

    val github = new GitHubImpl("127.0.0.1", 8000, 5, httpClient)
    val result = Source.single(HttpRequest(HttpMethods.GET, "/"))
      .via(github.send)
      .runWith(TestSink.probe[HttpResponse])
      .request(1)
      .expectNext()

    result shouldBe httpResponse
  }
}
