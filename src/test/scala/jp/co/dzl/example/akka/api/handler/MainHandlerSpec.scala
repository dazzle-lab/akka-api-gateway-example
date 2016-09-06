package jp.co.dzl.example.akka.api.handler

import akka.event.LoggingAdapter
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.circe.generic.auto._
import jp.co.dzl.example.akka.api.AkkaSupport
import jp.co.dzl.example.akka.api.response.MessageResponse
import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, FlatSpec }

class MainHandlerSpec extends FlatSpec with Matchers with ScalatestRouteTest with MockFactory with AkkaSupport {
  trait Subject extends MainHandler with HandlerTest {
    override val host = "127.0.0.1"
    override val port = 8080
    override val handlers = List()
  }

  "GET /" should "respond 200 OK with message" in new Subject {
    Get("/") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `application/json`
      responseAs[MessageResponse].message shouldEqual "Hello"
    }
  }

  "GET /" should "call logger" in new Subject {
    val infoMock = mockFunction[String, Unit]
    infoMock.expects(*).once()

    override lazy val logger = new LoggingAdapter {
      final override def isErrorEnabled = false
      final override def isWarningEnabled = false
      final override def isInfoEnabled = true
      final override def isDebugEnabled = false
      final protected override def notifyError(message: String): Unit = ()
      final protected override def notifyError(cause: Throwable, message: String): Unit = ()
      final protected override def notifyWarning(message: String): Unit = ()
      final protected override def notifyInfo(message: String): Unit = ()
      final protected override def notifyDebug(message: String): Unit = ()
      override def info(message: String) = infoMock(message)
    }

    Get("/") ~> routes
  }
}
