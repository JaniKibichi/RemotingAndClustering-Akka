package com.github.janikibichi.learnakka.remoting

import akka.actor.Actor
import akka.cluster.Cluster
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Subscribe, SubscribeAck}

case class Notification(title: String, body: String)

class NotificationSubscriber extends Actor {
  //Access mediator
  val mediator = DistributedPubSub(context.system).mediator

  //Send message to mediator to subscribe
  mediator ! Subscribe("notification", self)

  //Access cluster
  val cluster = Cluster(context.system)

  //Access cluster Address
  val clusterAddress = cluster.selfUniqueAddress

  def receive = {
    //Receive notification from publisher through mediator
    case notification: Notification =>
      println(s"Got notification in node $clusterAddress => $notification")

    case SubscribeAck(Subscribe("notification", None, `self`)) =>
      println("subscribing");
  }
}