package org.knoldus

import akka.actor.ActorSystem
import com.lightbend.lagom.scaladsl.akka.discovery.AkkaDiscoveryComponents
import com.lightbend.lagom.scaladsl.api.Descriptor
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.slick.SlickPersistenceComponents
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer, LagomServerComponents}
import com.softwaremill.macwire.wire
import play.api.db.HikariCPComponents
import play.api.libs.ws.ahc.AhcWSComponents
import scala.concurrent.ExecutionContext

class DemoLoader extends LagomApplicationLoader{
  override def load(context: LagomApplicationContext): LagomApplication = {
    new UserServiceApplication(context) with AkkaDiscoveryComponents
  }
  override def loadDevMode(context: LagomApplicationContext): LagomApplication = {
    new UserServiceApplication(context) with LagomDevModeComponents {
    }
  }
  override def describeService: Option[Descriptor] = Some(readDescriptor[Name2Service])
}

trait UserComponents
  extends LagomServerComponents
    with SlickPersistenceComponents
    with HikariCPComponents
    with AhcWSComponents {
  implicit def executionContext: ExecutionContext
  implicit def actorSystem: ActorSystem

  override lazy val jsonSerializerRegistry: UserSerializerRegistry.type = UserSerializerRegistry
}

abstract class UserServiceApplication(context: LagomApplicationContext) extends LagomApplication(context) with UserComponents {
  override lazy val lagomServer: LagomServer =
    serverFor[Name2Service](wire[Name2ServiceImpl])
}
object UserSerializerRegistry extends  JsonSerializerRegistry{
  override  def serializers:Seq[JsonSerializer[_]] = Seq.empty
}

