package com.github.janikibichi.learnakka.remoting

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}
import com.github.janikibichi.learnakka.remoting.TemperatureActor.{GetCurrentTemperature,Location,UpdateTemperature}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object ClusterShardingApp extends App {
  //Create an actorSystem for the app
  val actorSystem = ActorSystem("ClusterSystem")

  import actorSystem.dispatcher
  val temperatureActor: ActorRef = ClusterSharding(actorSystem).start(
    typeName = TemperatureActor.shardName,
    entityProps = Props[TemperatureActor],
    settings = ClusterShardingSettings(actorSystem),
    extractEntityId = TemperatureActor.extractEntityId,
    extractShardId = TemperatureActor.extractShardId
  )

  //Let's simulate some time has passed. Never use Thread.sleep in production
    Thread.sleep(30000)

  val locations = Vector(Location("USA", "Chicago"),Location("ESP","Madrid"), Location("FIN","Helsinki"))

  //Send Messages to the Actor
  temperatureActor ! UpdateTemperature(locations(0),1.0)
  temperatureActor ! UpdateTemperature(locations(1),20.0)
  temperatureActor ! UpdateTemperature(locations(2),-10.0)

  implicit val timeout = Timeout(5 seconds)
  locations.foreach{
    case location =>
      (temperatureActor ? GetCurrentTemperature(location)).onSuccess{
        case x: Double =>
          println(s"Current temperature in $location is $x")
      }
  }
}
