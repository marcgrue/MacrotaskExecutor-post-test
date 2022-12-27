import org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv

enablePlugins(ScalaJSPlugin)

lazy val rpc = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .settings(
    name := "rpc",
    organization := "com.github.marcgrue",
    scalaVersion := "2.13.10",
  )
  .jsSettings(
    testFrameworks += new TestFramework("utest.runner.Framework"),
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "2.1.0",
      "org.scala-js" %%% "scala-js-macrotask-executor" % "1.0.0",
      "com.lihaoyi" %%% "utest" % "0.7.11" % Test,

    ),
    // This alone gives "Error: connect ECONNREFUSED ::1:8080"
    //    jsEnv := new JSDOMNodeJSEnv()
    jsEnv := new JSDOMNodeJSEnv(
      JSDOMNodeJSEnv
        .Config()
        // for some reason still needed...
        // https://github.com/scala-js/scala-js-js-envs/issues/12
        .withArgs(List("--dns-result-order=ipv4first"))
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.7.0",
      "com.typesafe.akka" %% "akka-actor" % "2.7.0",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.7.0",
      "com.typesafe.akka" %% "akka-http" % "10.4.0",
      "ch.megard" %% "akka-http-cors" % "1.1.3",
    )
  )
