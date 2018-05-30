package com.github.janikibichi.learnakka.remoting

import akka.actor.{ActorSystem, Props}
import akka.cluster.Cluster
import scala.concurrent.duration._
import scala.util.Random

object DistributedPubSubApplication extends App {
  //Define actorSystem
  val actorSystem = ActorSystem("ClusterSystem")
  // Create cluster
  val cluster = Cluster(actorSystem)

  //Create and define actors in the actorSystem
  val notificationSubscriber = actorSystem.actorOf(Props[NotificationSubscriber])
  val notificationPublisher = actorSystem.actorOf(Props[NotificationPublisher])

  //Update cluster Address
  val clusterAddress = cluster.selfUniqueAddress
  val notification = Notification(s"Sent from $clusterAddress","Test!")

  //Schedule notifications
  import actorSystem.dispatcher
  actorSystem.scheduler.schedule(Random.nextInt(5) seconds, 5 seconds, notificationPublisher, notification)
}
