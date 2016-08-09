package jp.co.dzl.example.akka.api

import akka.actor.ActorSystem
import akka.stream.Materializer
import jp.co.dzl.example.akka.api.handlers.{ MainHandler, Handler }
import jp.co.dzl.example.akka.api.modules.{ ConfigModule, HandlerModule, AkkaModule }
import scaldi.{ Injector, Injectable }

import scala.concurrent.ExecutionContextExecutor

object Main extends App with MainHandler with Injectable {
  implicit val module: Injector = new AkkaModule :: new ConfigModule :: new HandlerModule

  override implicit val system = inject[ActorSystem]
  override implicit def executor = inject[ExecutionContextExecutor]
  override implicit val materializer = inject[Materializer]

  override val host = inject[String](identified by "http.listen.host")
  override val port = inject[Int](identified by "http.listen.port")
  override val handlers = inject[List[Handler]]

  bindAndHandle()
}
