package jp.co.dzl.example.akka.api

import akka.actor.ActorSystem
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

trait AkkaSupport { self =>
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  trait HandlerTest {
    implicit val system = self.system
    implicit def executor = self.executor
    implicit val materializer = self.materializer
  }
}
