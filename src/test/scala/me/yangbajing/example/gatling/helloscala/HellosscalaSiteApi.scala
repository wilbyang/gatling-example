package me.yangbajing.example.gatling.helloscala

import io.gatling.core.Predef._
import io.gatling.core.session.SessionAttribute
import io.gatling.http.Predef._
import me.yangbajing.example.gatling.utils.Utils

/**
 * External SDK API Test Suite
 * Created by jingyang on 15/8/4.
 */
object HellosscalaSiteApi {
  private val APIKEY = "APIKEY"
  private val SHARED_SECRET = "SHARED_SECRET"

  // 生成10个测试externalId
  val externalIds = (1 to 10).map(_ => Map("externalId" -> org.bson.types.ObjectId.get.toString))

  val createUser = resetTimestamp
    .exec(
      http("createUser")
        .post("/api/external/user")
        .header("sc-apikey", APIKEY)
        .header("sc-timestamp", "${timestamp}")
        .header("sc-api-token",
          session => computeToken("post", session("timestamp"), "/api/external/user"))
        .body(StringBody(
        """{
          |  "externalId":"${externalId}",
          |  "name":"用户1",
          |  "idCard":"50038119850000000X",
          |  "corporation":"企业1"
          |}""".stripMargin)).asJSON
        .check(status.is(201))
    )

  val socialSites = resetTimestamp
    .exec(
      http("postSocialSites")
        .post("/api/external/user/socialSites")
        .header("sc-apikey", APIKEY)
        .header("sc-timestamp", "${timestamp}")
        .header("sc-api-token",
          session => computeToken("post", session("timestamp"), "/api/external/user/socialSites"))
        .body(StringBody(
        """{
          |  "externalId": "${externalId}",
          |  "socialSites":[{
          |    "socialSite": "QQ",
          |    "accessToken": "lskjdflksdjflksdjf",
          |    "openId": "24234324",
          |    "expireIn": "23423"
          |  }]
          |}""".stripMargin)).asJSON
        .check(status.is(200))
    )

  val userLabels = resetTimestamp
    .exec(
      http("userLables")
        .get("/api/external/user/labels")
        .header("sc-apikey", APIKEY)
        .header("sc-timestamp", "${timestamp}")
        .header("sc-api-token",
          session =>
            computeToken("get", session("timestamp"), "/api/external/user/labels",
              "externalId=" + session("externalId").as[String])
        )
        .queryParam("externalId", "${externalId}")
        .check(status.is(200))
    )

  private def resetTimestamp = exec(session => session.map(_.set("timestamp", System.currentTimeMillis().toString)))

  private def computeToken(method: String, timestamp: SessionAttribute, apiPath: String, queryString: String = "") = {
    val ts = timestamp.as[String]
    println(s"$method, $ts, $apiPath, $queryString")
    Utils.hexSha256(method.toLowerCase + SHARED_SECRET + ts + apiPath + queryString)
  }

}
