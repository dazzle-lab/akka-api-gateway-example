package jp.co.dzl.example.akka.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ ActorMaterializer, Materializer }
import jp.co.dzl.example.akka.api.di.{ ServiceModule, HandlerModule, ConfigModule, AkkaModule }
import jp.co.dzl.example.akka.api.handler.RootHandler
import scaldi.{ Injector, Injectable }

import scala.concurrent.ExecutionContextExecutor

trait MainService extends Injectable {
  implicit val module: Injector =
    new AkkaModule :: new ConfigModule :: new HandlerModule :: new ServiceModule

  implicit val system: ActorSystem = inject[ActorSystem]
  implicit val executor: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()

  val host = inject[String](identified by "http.listen.host")
  val port = inject[Int](identified by "http.listen.port")
  val handler = inject[RootHandler]
}

object Main extends App with MainService {
  Http().bindAndHandle(handler.routes, host, port)
}
