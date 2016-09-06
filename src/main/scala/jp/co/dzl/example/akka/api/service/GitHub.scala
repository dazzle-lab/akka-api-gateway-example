package jp.co.dzl.example.akka.api.service

import akka.NotUsed
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import akka.stream.scaladsl.Flow

trait GitHub {
  def from(original: HttpRequest): Flow[HttpRequest, HttpRequest, NotUsed]
  def send: Flow[HttpRequest, HttpResponse, NotUsed]
}

class GitHubImpl(
    host:       String,
    port:       Int,
    timeout:    Int,
    httpClient: HttpClient
) extends GitHub {
  def from(original: HttpRequest): Flow[HttpRequest, HttpRequest, NotUsed] = Flow[HttpRequest].map { req =>
    val xForwardedHost = original.headers.find(_.is("host")).map(_.value()).getOrElse(s"$host:$port")
    val modifiedHeader = original.addHeader(RawHeader("X-Forwarded-Host", xForwardedHost))
      .headers
      .filterNot(_.lowercaseName() == "host")
      .filterNot(_.lowercaseName() == "timeout-access")

    req.withHeaders(modifiedHeader)
  }

  def send: Flow[HttpRequest, HttpResponse, NotUsed] =
    Flow[HttpRequest].via(httpClient.connectionHttps(host, port, timeout))
}
