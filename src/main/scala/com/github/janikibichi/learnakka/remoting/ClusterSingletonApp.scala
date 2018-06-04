package com.github.janikibichi.learnakka.remoting

import akka.actor.{ActorSystem, PoisonPill, Props}
import akka.cluster.Cluster
import akka.cluster.singleton._
import scala.concurrent.duration._

object ClusterSingletonApp extends App{
  val actorSystem = ActorSystem("ClusterSystem")
  val cluster = Cluster(actorSystem)

  val clusterSingletonSettings = ClusterSingletonManagerSettings(actorSystem)
  val clusterSingletonManager = ClusterSingletonManager.props(Props[ClusterAwareSimpleActor],PoisonPill, clusterSingletonSettings)

  actorSystem.actorOf(clusterSingletonManager, "singletonClusterAwareSimpleActor")

  val singletonSimpleActor = actorSystem.actorOf(ClusterSingletonProxy.props(
    singletonManagerPath="/user/singletonClusterAwareSimpleActor",
    settings = ClusterSingletonProxySettings(actorSystem)),
    name="singletonSimpleActorProxy")

  import actorSystem.dispatcher
  actorSystem.scheduler.schedule(10 seconds, 5 seconds, singletonSimpleActor, "TEST")
}
