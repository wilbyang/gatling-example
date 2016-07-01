package com.menusystems.uno.api.utils

import java.security.MessageDigest

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Utils {
  def gatlingHttp(server: String) =
    http
      .baseURL(server)
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
      .doNotTrackHeader("1")
      .acceptLanguageHeader("zh-CN,zh;q=0.8,en;q=0.6")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36")

  def hexSha256(s: String): String = {
    val d = getSha256
    d.update(s.getBytes("UTF-8"))
    bytes2hex16(d.digest())
  }

  def getMd5 = MessageDigest.getInstance("MD5")

  def getSha256 = MessageDigest.getInstance("SHA-256")

  def bytes2hex16(bytes: Array[Byte]): String = {
    require(bytes ne null)
    bytes.map(b => "%02x".format(b)).mkString
  }

}
