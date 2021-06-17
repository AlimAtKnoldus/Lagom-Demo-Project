package org.knoldus

import akka.actor.ActorSystem
import com.lightbend.lagom.scaladsl.akka.discovery.AkkaDiscoveryComponents
import com.lightbend.lagom.scaladsl.api._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.slick.SlickPersistenceComponents
import com.lightbend.lagom.scaladsl.playjson._
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.db.HikariCPComponents
import play.api.libs.ws.ahc.AhcWSComponents

import scala.concurrent.ExecutionContext

class DemoLoader extends LagomApplicationLoader{

  override def load(context: LagomApplicationContext): LagomApplication = {
    new DemoServiceApplication(context) with AkkaDiscoveryComponents
  }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication = {
    new DemoServiceApplication(context) with LagomDevModeComponents {
    }
  }
  override def describeService: Option[Descriptor] = Some(readDescriptor[DemoService])
}

trait DemoComponents
  extends LagomServerComponents
    with SlickPersistenceComponents
    with HikariCPComponents
    with AhcWSComponents {
  implicit def executionContext: ExecutionContext
  implicit def actorSystem: ActorSystem

  override lazy val jsonSerializerRegistry: DemoSerializer.type = DemoSerializer
}

abstract class DemoServiceApplication(context:LagomApplicationContext) extends LagomApplication(context) with DemoComponents {
  lazy val service2: Name2Service  = serviceClient.implement[Name2Service]

  override lazy val lagomServer: LagomServer = serverFor[DemoService](wire[DemoServiceImpl])
}

object DemoSerializer extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq.empty
}