package jp.co.dzl.example.akka.api.modules

import akka.actor.ActorSystem
import akka.stream.{ ActorMaterializer, Materializer }
import scaldi.Module

import scala.concurrent.ExecutionContextExecutor

class AkkaModule extends Module {
  bind[ActorSystem] to ActorSystem("akka-api-gateway-example")
  bind[ExecutionContextExecutor] to inject[ActorSystem].dispatcher
  bind[Materializer] to {
    implicit val system = inject[ActorSystem]
    ActorMaterializer()
  }
}
