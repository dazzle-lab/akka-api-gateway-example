package jp.co.dzl.example.akka.api.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import akka.stream.scaladsl.Flow

import scala.concurrent.duration._

trait HttpClient {
  def connectionHttps(host: String, port: Int, timeout: Int): Flow[HttpRequest, HttpResponse, _]
}

class HttpClientImpl(actorSystem: ActorSystem) extends HttpClient {
  implicit val system = actorSystem
  implicit val executor = system.dispatcher

  def connectionHttps(host: String, port: Int, timeout: Int): Flow[HttpRequest, HttpResponse, _] =
    Http().outgoingConnectionHttps(host, port).idleTimeout(timeout.seconds)
}
