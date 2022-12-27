package client

import utest._
import org.scalajs.dom
import scala.concurrent.{Future, Promise}

// Fails
import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits._

// Works
//import scala.concurrent.ExecutionContext.Implicits.global


object PostRequest extends TestSuite {

  lazy val post: Future[String] = {
    val req     = new dom.XMLHttpRequest()
    val promise = Promise[dom.XMLHttpRequest]()
    req.onreadystatechange = { (_: dom.Event) =>
      if (req.readyState == 4) {
        if ((req.status >= 200 && req.status < 300) || req.status == 304) {
          promise.success(req)
        } else {
          promise.failure(new Exception(req.toString))
        }
      }
    }
    req.open("POST", "http://localhost:8080/post/test")
    req.timeout = 0
    req.setRequestHeader("Content-Type", "application/json")
    req.send()
    promise.future.map(_.response.asInstanceOf[String])
  }

  lazy val tests = Tests {
    "exception test" - {
      for {
        _ <- post.map(_ ==> "hello world")
      } yield ()
    }
  }
}
