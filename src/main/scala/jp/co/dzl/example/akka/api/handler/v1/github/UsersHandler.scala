package jp.co.dzl.example.akka.api.handler.v1.github

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.{ Sink, Source }
import jp.co.dzl.example.akka.api.handler.Handler

import scala.concurrent.Future
import scala.concurrent.duration._

trait UsersHandler extends Handler {
  val host: String
  val port: Int
  val timeout: Int

  def github(request: HttpRequest): Future[HttpResponse] = {
    val connection = Http().outgoingConnectionHttps(host, port).idleTimeout(timeout.seconds)
    Source.single(request).via(connection).runWith(Sink.head)
  }

  def modifyHeaders(request: HttpRequest) = {
    val host = request.headers.find(_.is("host")).map(_.value())

    request
      .addHeader(RawHeader("X-Forwarded-Host", host.getOrElse(s"$host:$port")))
      .headers
      .filterNot(_.lowercaseName() == "host")
      .filterNot(_.lowercaseName() == "timeout-access")
  }

  override def routes = pathPrefix("v1") {
    pathPrefix("github") {
      path("users" / """^[a-zA-Z0-9\-]+$""".r) { login =>
        get { context =>
          val request = HttpRequest(HttpMethods.GET, s"/users/$login").withHeaders(modifyHeaders(context.request))

          github(request)
            .flatMap(context.complete(_))
            .fallbackTo(context.complete(StatusCodes.ServiceUnavailable))
        }
      }
    }
  }
}
