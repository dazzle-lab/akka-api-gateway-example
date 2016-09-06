package jp.co.dzl.example.akka.api.di

import akka.actor.ActorSystem
import scaldi.Module

class AkkaModule extends Module {
  bind[ActorSystem] toNonLazy ActorSystem("akka-api-gateway-example")
}
