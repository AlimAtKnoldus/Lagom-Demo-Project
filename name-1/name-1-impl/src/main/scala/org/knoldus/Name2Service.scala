package org.knoldus

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method.{GET, POST}
import com.lightbend.lagom.scaladsl.api._


trait Name2Service extends Service {

  def Greeting: ServiceCall[NotUsed, String]

  override def descriptor: Descriptor = {
    import Service._
    named("name-2")
      .withCalls(
        restCall(GET, "/api/validate/", Greeting _)
      )
  }
}
