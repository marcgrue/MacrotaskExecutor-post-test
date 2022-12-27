### Test problem with MacrotaskExecutor and XMLHttpRequest post

Start Akka Http server in one terminal tab:

    sbt
    rpcJVM/run

Test failing MacrotaskExecutor implicit in another tab:

    sbt -client
    project rpcJS
    test

Switch execution context by uncommenting/commenting imports in `PostRequest` to: 
    
    import scala.concurrent.ExecutionContext.Implicits.global
    //import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits._