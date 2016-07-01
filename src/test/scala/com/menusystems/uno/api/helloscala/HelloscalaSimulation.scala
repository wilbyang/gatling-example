package com.menusystems.uno.api.helloscala

import com.menusystems.uno.api.utils.Utils
import io.gatling.core.Predef._
import Utils

/**
 * Helloscala Site Test Suites
 * Created by jingyang on 15/8/4.
 */
class HelloscalaSimulation extends Simulation {
  val httpConf = Utils.gatlingHttp("http://localhost:19001")

  val helloscalaSDKApiScenario = scenario("HelloscalaSDKApi")
    .feed(HellosscalaSiteApi.externalIds)
    .exec(
      HellosscalaSiteApi.createUser,
      HellosscalaSiteApi.socialSites,
      HellosscalaSiteApi.userLabels
    )

  val userSize = HellosscalaSiteApi.externalIds.size

  setUp(
    helloscalaSDKApiScenario.inject(atOnceUsers(userSize))
  ).protocols(httpConf)

}
