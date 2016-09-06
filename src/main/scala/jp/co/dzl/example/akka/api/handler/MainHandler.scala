package jp.co.dzl.example.akka.api.handler

import akka.event.Logging
import io.circe.generic.auto._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.CirceSupport
import jp.co.dzl.example.akka.api.response.MessageResponse

trait MainHandler extends Handler with CirceSupport {
  val host: String
  val port: Int
  val handlers: List[Handler]
  lazy val logger = Logging(system, "MainHandler")

  override def routes = pathSingleSlash {
    get {
      logger.info("Logging test")
      complete(MessageResponse(message = "Hello"))
    }
  }

  def bindAndHandle() = Http().bindAndHandle(handlers.foldRight(routes)(_.routes ~ _), host, port)
}
