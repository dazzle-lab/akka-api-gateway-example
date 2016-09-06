package jp.co.dzl.example.akka.api

import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ BeforeAndAfterAll, Matchers, FlatSpec }

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MainServiceSpec extends FlatSpec with Matchers with BeforeAndAfterAll with ScalaFutures with MainService {
  override protected def afterAll: Unit = {
    Await.result(system.terminate(), Duration.Inf)
  }

  it should "inject configuration of http" in {
    val config = ConfigFactory.load()

    host shouldEqual config.getString("http.listen.host")
    port shouldEqual config.getInt("http.listen.port")
  }

  it should "bind and handle" in {
    val http = Http().bindAndHandle(handler.routes, host, port)
    http.futureValue.localAddress.getPort shouldEqual port
    http.futureValue.unbind()
  }
}
