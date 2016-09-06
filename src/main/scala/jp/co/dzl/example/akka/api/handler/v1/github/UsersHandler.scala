package jp.co.dzl.example.akka.api.handler.v1.github

import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ Sink, Source }
import jp.co.dzl.example.akka.api.handler.Handler
import jp.co.dzl.example.akka.api.service.GitHub

import scala.util.{ Failure, Success }

class UsersHandler(
    actorSystem: ActorSystem,
    github:      GitHub
) extends Handler {
  implicit val system = actorSystem
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  def routes = pathPrefix("v1" / "github") {
    path("users" / """^[a-zA-Z0-9\-]+$""".r) { login =>
      get {
        extractRequest { req =>
          val result = Source.single(HttpRequest(HttpMethods.GET, s"/users/$login"))
            .via(github.from(req))
            .via(github.send)
            .runWith(Sink.head)

          onComplete(result) {
            case Success(response) => complete(response)
            case Failure(error)    => complete(StatusCodes.ServiceUnavailable -> error.toString)
          }
        }
      }
    }
  }
}
