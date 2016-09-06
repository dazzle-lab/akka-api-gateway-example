package jp.co.dzl.example.akka.api.service

import akka.actor.ActorSystem
import akka.stream.scaladsl.Flow
import org.scalatest.{ BeforeAndAfterAll, Matchers, FlatSpec }

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class HttpClientSpec extends FlatSpec with Matchers with BeforeAndAfterAll {
  implicit val system = ActorSystem("http-client-spec")
  implicit val executor = system.dispatcher

  override protected def afterAll: Unit = {
    Await.result(system.terminate(), Duration.Inf)
  }

  "#conectionHttps" should "return outgoing connection flow" in {
    val httpClient = new HttpClientImpl(system)
    val connection = httpClient.connectionHttps("127.0.0.1", 8000, 5)

    connection shouldBe a[Flow[_, _, _]]
  }
}
