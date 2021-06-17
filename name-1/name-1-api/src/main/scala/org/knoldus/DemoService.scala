package org.knoldus

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method.GET
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait DemoService extends Service {

  def receiveGreeting: ServiceCall[NotUsed, String]

  override def descriptor: Descriptor = {
    import com.lightbend.lagom.scaladsl.api.Service._
    named("name-1").withCalls(
      restCall(GET, "/api/validate/", receiveGreeting _)
    )
  }
}
