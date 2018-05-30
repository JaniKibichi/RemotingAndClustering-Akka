package com.github.janikibichi.learnakka.remoting

import akka.actor.Actor
import akka.cluster.Cluster
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Subscribe, SubscribeAck}

case class Notification(title: String, body: String)

class NotificationSubscriber extends Actor {
  //Create mediator
  val mediator = DistributedPubSub(context.system).mediator

  //Send message
  mediator ! Subscribe("notification", self)

  //Create cluster
  val cluster = Cluster(context.system)

  //Get cluster Address
  val clusterAddress = cluster.selfUniqueAddress

  def receive = {
    case notification: Notification =>
      println(s"Got notification in node $clusterAddress => $notification")

    case SubscribeAck(Subscribe("notification", None, `self`)) =>
      println("subscribing");
  }
}