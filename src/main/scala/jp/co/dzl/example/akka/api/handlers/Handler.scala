package jp.co.dzl.example.akka.api.handlers

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

trait Handler {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def routes: Route
}
