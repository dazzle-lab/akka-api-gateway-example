package jp.co.dzl.example.akka.api.handler

import akka.http.scaladsl.server.Route

trait Handler {
  def routes: Route
}
