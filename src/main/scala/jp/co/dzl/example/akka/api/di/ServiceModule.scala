package jp.co.dzl.example.akka.api.di

import akka.actor.ActorSystem
import jp.co.dzl.example.akka.api.service.{ HttpClientImpl, HttpClient, GitHubImpl, GitHub }
import scaldi.Module

class ServiceModule extends Module {
  bind[HttpClient] to new HttpClientImpl(
    actorSystem = inject[ActorSystem]
  )

  bind[GitHub] to new GitHubImpl(
    host = inject[String](identified by "services.github.host"),
    port = inject[Int](identified by "services.github.port"),
    timeout = inject[Int](identified by "services.github.timeout"),
    httpClient = inject[HttpClient]
  )
}
