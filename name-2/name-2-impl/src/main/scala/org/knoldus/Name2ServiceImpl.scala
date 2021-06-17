package org.knoldus

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import scala.concurrent.{ExecutionContext, Future}

class Name2ServiceImpl(implicit ec: ExecutionContext) extends Name2Service {

  override def Greeting: ServiceCall[NotUsed, String] = { _ =>
    Future {
      "Hello!"
    }
  }
}
