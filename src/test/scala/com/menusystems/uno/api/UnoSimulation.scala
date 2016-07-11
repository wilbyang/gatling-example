package com.menusystems.uno.api

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import scala.concurrent.duration._

/**
  * Created by wilbyang on 01/07/16.
  */
class UnoSimulation extends Simulation{

  val users = csv("users.csv").random


  val httpConf = http
    .baseURL("https://api.justunoit.com/rest/v1")
      .basicAuth("admin@justunoit.com", "yangbo")
        //.feed(users) no such function


  val scn = scenario("Uno Consumer workflow") // A scenario is a chain of requests and pauses
    .exec(http("login")
    .get("/me"))
    .exec(http("access venues").get("/venues"))
    .exec(http("access one venue(Langolo)").get("/venues/849157c4-73e8-4523-94a2-058925c27345"))





  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))


}
