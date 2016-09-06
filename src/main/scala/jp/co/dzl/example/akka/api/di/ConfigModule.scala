package jp.co.dzl.example.akka.api.di

import com.typesafe.config.{ ConfigFactory, Config }
import scaldi.Module

class ConfigModule extends Module {
  bind[Config] to ConfigFactory.load()

  bind[String] identifiedBy "http.listen.host" to inject[Config].getString("http.listen.host")
  bind[Int] identifiedBy "http.listen.port" to inject[Config].getInt("http.listen.port")

  bind[String] identifiedBy "services.github.host" to inject[Config].getString("services.github.host")
  bind[Int] identifiedBy "services.github.port" to inject[Config].getInt("services.github.port")
  bind[Int] identifiedBy "services.github.timeout" to inject[Config].getInt("services.github.timeout")
}
