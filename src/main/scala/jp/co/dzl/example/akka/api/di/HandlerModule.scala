package jp.co.dzl.example.akka.api.di

import akka.actor.ActorSystem
import jp.co.dzl.example.akka.api.handler.{ RootHandler, Handler }
import jp.co.dzl.example.akka.api.handler.v1.github.UsersHandler
import jp.co.dzl.example.akka.api.service.GitHub
import scaldi.Module

class HandlerModule extends Module {
  bind[UsersHandler] to new UsersHandler(
    actorSystem = inject[ActorSystem],
    github = inject[GitHub]
  )

  bind[List[Handler]] to List(
    inject[UsersHandler]
  )

  bind[RootHandler] to new RootHandler(
    handlers = inject[List[Handler]]
  )
}
