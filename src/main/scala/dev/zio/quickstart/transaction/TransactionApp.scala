package dev.zio.quickstart.transaction

import zhttp.http.*
import zio.*
import zio.json.*

object TransactionApp:
  def apply(): Http[Any, Throwable, Request, Response] =
    Http.collect[Request] {
      // POST /transaction-check "{\"src\":\"1\",\"dst\":\"5\",\"amount\":10}"
      case req@(Method.POST -> !! / "transaction-check") =>
        val json = "{\"src\":\"6\",\"dst\":\"5\",\"amount\":10}"
        val s = JsonDecoder[Transaction].decodeJson(json)
        val transaction = s.toSeq.last
        val src = transaction.src
        val dst = transaction.dst
        var isFlag = false
        for (v <- List("1", "2", "3")) {
          if src == v || dst == v then
            isFlag = true
        }
        if isFlag then
          Response.text("Cancel").setStatus(Status.BadRequest)
        else
          Response.text("Success").setStatus(Status.BadRequest)
    }