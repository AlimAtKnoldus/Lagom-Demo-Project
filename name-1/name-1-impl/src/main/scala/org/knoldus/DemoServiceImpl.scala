package org.knoldus

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import scala.concurrent.ExecutionContext

class DemoServiceImpl(userService:Name2Service)(implicit ec:ExecutionContext) extends DemoService {

  override def receiveGreeting: ServiceCall[NotUsed, String] = ServiceCall{_ =>
    userService.Greeting.invoke()
  }

}
