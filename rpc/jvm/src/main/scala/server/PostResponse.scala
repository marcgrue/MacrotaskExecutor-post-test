package server

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import scala.util.{Failure, Success}


object PostResponse extends App  {
  implicit val system           = ActorSystem(Behaviors.empty, "actorSystem")
  implicit val executionContext = system.executionContext

  Http()
    .newServerAt("localhost", 8080)
    .bind {
      cors() {
        path("post" / "test").apply {
          post {
            extractRequest { _ =>
              complete("hello world")
            }
          }
        }
      }
    }
    .onComplete {
      case Success(b) => println(s"Ajax server is running ${b.localAddress} ")
      case Failure(e) => println(s"there was an error starting the server $e")
    }
}
