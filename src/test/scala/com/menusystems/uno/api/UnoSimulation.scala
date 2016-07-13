/**
  * Copyright 2011-2016 GatlingCorp (http://gatling.io)
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *  http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.menusystems.uno.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class UnoSimulation extends Simulation {

  object Login {

    val consumers = csv("consumers.csv").random
    val staff = csv("staff.csv").random

    val consumerLogin = feed(consumers)
      .exec(http("Login").get("/me").basicAuth("${email}", "${pwd}"))
      .pause(1)


    val staffLogin = feed(staff)
      .exec(http("Login").get("/me").basicAuth("${email}", "${pwd}"))
      .pause(1)
  }
  object Browse {

    val browse = exec(http("get venue list")
      .get("/venues"))
      .pause(2)
      .exec(http("get SKYBAR")
        .get("/venues/f7826da6-4fa2-4e98-8024-bc5b71e0893e"))
      .pause(670 milliseconds)
      .exec(http("get MARQUEEN")
        .get("/venues/b532b01f-07bc-11e6-8c0b-064136249581"))
      .pause(734 milliseconds)
      .exec(http("Seeing MARQUEE Menus")
        .get("/venues/b532b01f-07bc-11e6-8c0b-064136249581/menus"))
      .pause(5)
  }

  object Orders {

    val checking =
      exec(http("get order list").get("/")).pause(2)



    val operation =
      exec(http("confirm").get("/")).pause(2)//confirm order
        .exec(http("deliver").get("/")).pause(2)//deliver order

    //deliver order
    //
  }


  val httpConf = http
    .baseURL("http://dev.justunoit.com/rest/v1")

  val consumers = scenario("Consumers").exec(Login.consumerLogin, Browse.browse)
  //val admins = scenario("Staffs").exec(Search.search, Browse.browse, Edit.edit)
  setUp(
    consumers.inject(rampUsers(200) over (100 seconds))
  ).protocols(httpConf)
}
