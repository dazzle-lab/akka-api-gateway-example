package jp.co.dzl.example.akka.api.modules

import akka.actor.ActorSystem
import akka.stream.Materializer
import jp.co.dzl.example.akka.api.handlers.Handler
import jp.co.dzl.example.akka.api.handlers.v1.github.UsersHandler
import scaldi.Module

import scala.concurrent.ExecutionContextExecutor

class HandlerModule extends Module {
  bind[UsersHandler] to new UsersHandler with HandlerInjection {
    override val host = inject[String](identified by "services.github.host")
    override val port = inject[Int](identified by "services.github.port")
    override val timeout = inject[Int](identified by "services.github.timeout")
  }

  bind[List[Handler]] to List(
    inject[UsersHandler]
  )

  trait HandlerInjection {
    implicit val system: ActorSystem = inject[ActorSystem]
    implicit def executor: ExecutionContextExecutor = inject[ExecutionContextExecutor]
    implicit val materializer: Materializer = inject[Materializer]
  }
}
